package it.fulminazzo.javaparser.typechecker;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.environment.ScopeException;
import it.fulminazzo.javaparser.environment.scopetypes.ScopeType;
import it.fulminazzo.javaparser.parser.node.Assignment;
import it.fulminazzo.javaparser.parser.node.MethodInvocation;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.parser.node.container.JavaProgram;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import it.fulminazzo.javaparser.parser.node.statements.CaseStatement;
import it.fulminazzo.javaparser.parser.node.statements.CatchStatement;
import it.fulminazzo.javaparser.parser.node.statements.Return;
import it.fulminazzo.javaparser.parser.node.statements.Statement;
import it.fulminazzo.javaparser.typechecker.types.*;
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayClassType;
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayType;
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType;
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType;
import it.fulminazzo.javaparser.visitors.Visitor;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.Callable;

import static it.fulminazzo.javaparser.typechecker.OperationUtils.*;
import static it.fulminazzo.javaparser.typechecker.types.ValueType.*;

/**
 * A {@link Visitor} that checks and verifies all the types of the parsed code.
 */
public final class TypeChecker implements Visitor<Type> {
    private static final ScopeType[] BREAK_SCOPES = new ScopeType[] {
            ScopeType.DO, ScopeType.WHILE, ScopeType.FOR, ScopeType.SWITCH, ScopeType.CASE
    };
    private static final ScopeType[] CONTINUE_SCOPES = new ScopeType[] {
            ScopeType.DO, ScopeType.WHILE, ScopeType.FOR
    };
    private static final String FIELDS_SEPARATOR = ".";

    private final Object executingObject;
    private final Environment<Type> environment;

    /**
     * Instantiates a new Type checker.
     *
     * @param executingObject the executing object
     */
    public TypeChecker(final @NotNull Object executingObject) {
        this.executingObject = executingObject;
        this.environment = new Environment<>();
    }

    /**
     * Starting point of the {@link Visitor}.
     * It reads the given {@link JavaProgram} using all the methods in this class.
     *
     * @param program the program
     * @return an {@link Optional} with the returned type if it is not equal to {@link Types#NO_TYPE}
     */
    @Override
    public @NotNull Optional<Type> visitProgram(@NotNull JavaProgram program) {
        Type type = program.accept(this);
        return type.equals(Types.NO_TYPE) ? Optional.empty() : Optional.of(type);
    }

    /**
     * Visits all the {@link Assignment}s and returns a {@link ParameterTypes} with all
     * the {@link ClassType}s of the assignments in it.
     *
     * @param assignments the assignments
     * @return the {@link ParameterTypes}
     */
    @Override
    public @NotNull Type visitAssignmentBlock(@NotNull List<Assignment> assignments) {
        List<ClassType> types = new LinkedList<>();
        for (Assignment assignment : assignments) {
            assignment.accept(this);
            types.add(assignment.getType().accept(this).checkClassType());
        }
        return new ParameterTypes(types);
    }

    @Override
    public @NotNull Type visitAssignment(@NotNull Node type, @NotNull Literal name, @NotNull Node value) {
        ClassType variableType = type.accept(this).checkClassType();
        Type tempVariableName = name.accept(this);
        if (!tempVariableName.is(LiteralType.class))
            throw TypeCheckerException.of(ScopeException.alreadyDeclaredVariable(name.getLiteral()));
        LiteralType variableName = tempVariableName.check(LiteralType.class);
        Type variableValue = value.accept(this);
        // Test for uninitialized
        if (variableValue.is(Types.NO_TYPE))
            if (variableType.is(PrimitiveType.class)) variableValue = variableType.toType();
            else variableValue = Types.NULL_TYPE;
        variableValue = convertByteAndShort(variableType, variableValue);
        if (variableValue.isAssignableFrom(variableType)) {
            try {
                this.environment.declare(variableType, variableName.getLiteral(), convertValue(variableType, variableValue));
            } catch (ScopeException ignored) {}
            return Types.NO_TYPE;
        } else throw TypeCheckerException.invalidType(variableType.toType(), variableValue);
    }

    /**
     * Converts the given {@link Type} to the most appropriate one.
     * If it is NOT {@link ValueType}, it is returned as is.
     * Otherwise, if the {@link ClassType} is:
     * <ul>
     *     <li>a {@link PrimitiveType}, {@link PrimitiveType#toType()} is returned;</li>
     *     <li>a {@link ClassObjectType}, {@link ClassObjectType#toType()} is returned only
     *     if it is NOT {@link ClassObjectType#OBJECT}.</li>
     * </ul>
     *
     * @param valueType the type of the value
     * @param value     the value
     * @return the value converted
     */
    static @NotNull Type convertValue(final @NotNull ClassType valueType,
                                      final @NotNull Type value) {
        if (!value.isValue()) return value;
        else if (valueType.is(PrimitiveType.class)) return valueType.toType();
        else if (!valueType.is(ClassObjectType.OBJECT)) // Can only be ClassObjectType at this point
            return valueType.toType();
        return value;
    }

    /**
     * Support function for {@link #visitAssignment(Node, Literal, Node)}
     * and {@link #visitReAssign(Node, Node)}.
     * Converts {@link ValueType}s for {@link PrimitiveType#BYTE} and {@link PrimitiveType#SHORT}.
     *
     * @param variableType  the variable type
     * @param variableValue the variable value
     * @return the type
     */
    static @NotNull Type convertByteAndShort(final @NotNull ClassType variableType,
                                             final @NotNull Type variableValue) {
        if (variableType.is(PrimitiveType.BYTE, ClassObjectType.BYTE, PrimitiveType.SHORT, ClassObjectType.SHORT))
            if (variableValue.is(NUMBER) || variableValue.is(CHAR)) return variableType.toType();
        return variableValue;
    }

    @Override
    public @NotNull Type visitMethodCall(@NotNull Node executor, @NotNull String methodName,
                                         @NotNull MethodInvocation invocation) {
        try {
            Type type = executor.accept(this);
            if (type.is(Types.NO_TYPE)) type = ObjectType.of(this.executingObject.getClass());
            return type.getMethod(methodName, (ParameterTypes) invocation.accept(this)).toType();
        } catch (TypeException e) {
            throw TypeCheckerException.of(e);
        }
    }

    @Override
    public @NotNull Type visitField(@NotNull Node left, @NotNull Node right) {
        try {
            return left.accept(this).getField(right.accept(this).check(LiteralType.class).getLiteral());
        } catch (TypeException e) {
            throw TypeCheckerException.of(e);
        }
    }

    @Override
    public @NotNull Type visitMethodInvocation(@NotNull List<Node> parameters) {
        List<ClassType> parameterTypes = new LinkedList<>();
        for (Node parameter : parameters)
            parameterTypes.add(parameter.accept(this).toClassType());
        return new ParameterTypes(parameterTypes);
    }

    @Override
    public @NotNull Type visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type) {
        ClassType componentType = type.accept(this).check(ArrayClassType.class).getComponentType();
        for (Node parameter : parameters) parameter.accept(this).isAssignableFrom(componentType);
        return new ArrayType(componentType.toType());
    }

    @Override
    public @NotNull Type visitStaticArray(int size, @NotNull Node type) {
        ClassType componentType = type.accept(this).checkClassType();
        if (size < 0) throw TypeCheckerException.invalidArraySize(size);
        else return new ArrayType(componentType.toType());
    }

    @Override
    public @NotNull Type visitCodeBlock(@NotNull LinkedList<Statement> statements) {
        return visitScoped(ScopeType.CODE_BLOCK, () -> {
            Type type = Types.NO_TYPE;
            for (Statement statement : statements) {
                Type checked = statement.accept(this);
                if (statement.is(Return.class)) type = checked;
            }
            return type;
        });
    }

    @Override
    public @NotNull Type visitJavaProgram(@NotNull LinkedList<Statement> statements) {
        return visitCodeBlock(statements);
    }

    @Override
    public @NotNull Type visitArrayLiteral(@NotNull Node type) {
        return new ArrayClassType(type.accept(this).checkClassType());
    }

    @Override
    public @NotNull Type visitEmptyLiteral() {
        return Types.NO_TYPE;
    }

    @Override
    public @NotNull Type visitLiteralImpl(@NotNull String value) {
        @NotNull Tuple<ClassType, Type> tuple = getTypeFromLiteral(value);
        if (value.contains(FIELDS_SEPARATOR)) {
            // Class was parsed
            if (tuple.isPresent()) return tuple.getValue();

            LinkedList<String> first = new LinkedList<>(Arrays.asList(value.split("\\" + FIELDS_SEPARATOR)));
            LinkedList<String> last = new LinkedList<>();

            while(!first.isEmpty()) {
                last.addFirst(first.removeLast());

                tuple = getTypeFromLiteral(String.join(".", first));
                if (tuple.isPresent())
                    try {
                        ClassType field = tuple.getKey().getField(last.removeFirst());
                        while (!last.isEmpty()) field = field.getField(last.removeFirst());
                        return field.toType();
                    } catch (TypeException e) {
                        throw TypeCheckerException.of(e);
                    }
            }
            throw TypeCheckerException.cannotResolveSymbol(value);
        } else {
            if (tuple.isPresent()) return tuple.getValue();
            else return new LiteralType(value);
        }
    }

    @Override
    public @NotNull Type visitAdd(@NotNull Node left, @NotNull Node right) {
        Type leftType = left.accept(this);
        if (leftType.is(STRING)) return right.accept(this).check(STRING);
        else return executeBinaryOperationDecimal(leftType, right.accept(this));
    }

    @Override
    public @NotNull Type visitAnd(@NotNull Node left, @NotNull Node right) {
        left.accept(this).check(BOOLEAN);
        right.accept(this).check(BOOLEAN);
        return BOOLEAN;
    }

    @Override
    public @NotNull Type visitBitAnd(@NotNull Node left, @NotNull Node right) {
        return executeBinaryBitOperation(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitBitOr(@NotNull Node left, @NotNull Node right) {
        return executeBinaryBitOperation(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitBitXor(@NotNull Node left, @NotNull Node right) {
        return executeBinaryBitOperation(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitCast(@NotNull Node left, @NotNull Node right) {
        ClassType cast = left.accept(this).checkClassType();
        Type type = right.accept(this);
        return cast.cast(type);
    }

    @Override
    public @NotNull Type visitDivide(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperationDecimal(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitEqual(@NotNull Node left, @NotNull Node right) {
        return executeObjectComparison(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitGreaterThan(@NotNull Node left, @NotNull Node right) {
        return executeBinaryComparison(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitGreaterThanEqual(@NotNull Node left, @NotNull Node right) {
        return executeBinaryComparison(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitLShift(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperation(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitLessThan(@NotNull Node left, @NotNull Node right) {
        return executeBinaryComparison(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitLessThanEqual(@NotNull Node left, @NotNull Node right) {
        return executeBinaryComparison(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitModulo(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperationDecimal(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitMultiply(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperationDecimal(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitNewObject(@NotNull Node left, @NotNull Node right) {
        try {
            return left.accept(this).checkClassType().newObject(right.accept(this).check(ParameterTypes.class));
        } catch (TypeException e) {
            throw TypeCheckerException.of(e);
        }
    }

    @Override
    public @NotNull Type visitNotEqual(@NotNull Node left, @NotNull Node right) {
        return executeObjectComparison(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitOr(@NotNull Node left, @NotNull Node right) {
        left.accept(this).check(BOOLEAN);
        right.accept(this).check(BOOLEAN);
        return BOOLEAN;
    }

    @Override
    public @NotNull Type visitRShift(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperation(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitReAssign(@NotNull Node left, @NotNull Node right) {
        try {
            String variableName = ((Literal) left).getLiteral();
            ClassType variableType = (ClassType) this.environment.lookupInfo(variableName);
            Type variableValue = convertByteAndShort(variableType, right.accept(this));
            if (variableValue.isAssignableFrom(variableType)) {
                variableValue = convertValue(variableType, variableValue);
                this.environment.update(variableName, variableValue);
                return variableType.toType();
            } else throw TypeCheckerException.invalidType(variableType.toType(), variableValue);
        } catch (ScopeException e) {
            throw TypeCheckerException.of(e);
        }
    }

    @Override
    public @NotNull Type visitSubtract(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperationDecimal(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitURShift(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperation(left.accept(this), right.accept(this));
    }

    @Override
    public @NotNull Type visitDecrement(boolean before, @NotNull Node operand) {
        return operand.accept(this).check(getDecimalTypes());
    }

    @Override
    public @NotNull Type visitIncrement(boolean before, @NotNull Node operand) {
        return operand.accept(this).check(getDecimalTypes());
    }

    @Override
    public @NotNull Type visitMinus(@NotNull Node operand) {
        Type type = operand.accept(this);
        if (type.is(CHAR)) return NUMBER;
        return type.check(getDecimalTypes());
    }

    @Override
    public @NotNull Type visitNot(@NotNull Node operand) {
        return operand.accept(this).check(BOOLEAN);
    }

    @Override
    public @NotNull Type visitBreak(@NotNull Node expression) {
        try {
            this.environment.check(BREAK_SCOPES);
        } catch (ScopeException e) {
            throw TypeCheckerException.of(e);
        }
        return Types.NO_TYPE;
    }

    @Override
    public @NotNull Type visitContinue(@NotNull Node expression) {
        try {
            this.environment.check(CONTINUE_SCOPES);
        } catch (ScopeException e) {
            throw TypeCheckerException.of(e);
        }
        return Types.NO_TYPE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull Type visitTryStatement(@NotNull CodeBlock block, @NotNull List<CatchStatement> catchBlocks,
                                           @NotNull CodeBlock finallyBlock, @NotNull Node expression) {
        return visitScoped(ScopeType.TRY, () -> {
            final ClassType autoClosable = ClassType.of(AutoCloseable.class);

            ParameterTypes assignments = expression.accept(this).check(ParameterTypes.class);
            for (Class<?> assignment : assignments.toJavaClassArray()) {
                if (!autoClosable.toJavaClass().isAssignableFrom(assignment))
                    throw TypeCheckerException.invalidType(autoClosable, ClassType.of(assignment));
            }

            Type returnType = null;
            LinkedHashSet<ClassType> caughtExceptions = new LinkedHashSet<>();
            for (CatchStatement catchStatement : catchBlocks) {
                TupleType<Set<ClassType>, Type> tuple = catchStatement.accept(this).check(TupleType.class);

                Type catchType = tuple.getValue();
                if (returnType == null) returnType = catchType;
                else if (!catchType.is(returnType)) returnType = Types.NO_TYPE;

                for (ClassType exception : tuple.getKey()) {
                    if (caughtExceptions.stream().anyMatch(e -> e.compatibleWith(exception.toType())))
                        throw TypeCheckerException.exceptionAlreadyCaught(exception);
                    else caughtExceptions.add(exception);
                }
            }

            Type visitedType = visitScoped(ScopeType.tryScope(caughtExceptions.stream()
                    .map(ClassType::toJavaClass)
                    .map(c -> (Class<Throwable>) c)), () -> block.accept(this));
            returnType = returnType == null || visitedType.is(returnType) ? visitedType : Types.NO_TYPE;

            Type finallyType = finallyBlock.accept(this);
            return !finallyType.is(Types.NO_TYPE) ? finallyType : returnType;
        });
    }

    /**
     * Visits a {@link CatchStatement}.
     * It checks that all the passed exceptions are not duplicated and extend {@link Throwable}.
     * Then, it obtains a {@link LiteralType} from the expression,
     * and it declares a new variable with type the first exception and name the one from the literal.
     *
     * @param exceptions the exceptions
     * @param block      the block
     * @param expression the expression
     * @return a {@link TupleType} containing a list of all the caught exceptions and
     * the returned type of the {@link CodeBlock}
     */
    @Override
    public @NotNull TupleType<Set<ClassType>, Type> visitCatchStatement(@NotNull List<Literal> exceptions,
                                                                         @NotNull CodeBlock block,
                                                                         @NotNull Node expression) {
        return visitScoped(ScopeType.CATCH, () -> {
            final ClassType throwable = ClassType.of(Throwable.class);

            final List<ClassType> exceptionTypes = new LinkedList<>();
            for (Literal exception : exceptions) {
                ClassType exceptionClass = exception.accept(this).checkClassType();
                Type exceptionType = exceptionClass.toType();

                if (!exceptionType.isAssignableFrom(throwable))
                    throw TypeCheckerException.invalidType(throwable, exceptionClass);
                else if (exceptionTypes.contains(exceptionClass))
                    throw TypeCheckerException.exceptionAlreadyCaught(exceptionClass);

                ClassType inheritedType = exceptionTypes.stream()
                        .filter(e -> e.compatibleWith(exceptionType))
                        .findFirst().orElse(null);
                if (inheritedType != null)
                    throw TypeCheckerException.exceptionsNotDisjoint(exceptionClass, inheritedType);

                ClassType inheritingType = exceptionTypes.stream()
                        .filter(e -> e.toType().isAssignableFrom(exceptionClass))
                        .findFirst().orElse(null);
                if (inheritingType != null)
                    throw TypeCheckerException.exceptionsNotDisjoint(inheritingType, exceptionClass);

                exceptionTypes.add(exceptionClass);
            }

            try {
                Literal literal = (Literal) expression;
                String exceptionName = literal.getLiteral();

                if (!literal.accept(this).is(LiteralType.class))
                    throw ScopeException.alreadyDeclaredVariable(exceptionName);

                this.environment.declare(exceptionTypes.get(0), exceptionName, exceptionTypes.get(0).toType());
            } catch (ScopeException e) {
                throw TypeCheckerException.of(e);
            }

            return new TupleType<>(new LinkedHashSet<>(exceptionTypes), block.accept(this));
        });
    }

    @Override
    public @NotNull Type visitSwitchStatement(@NotNull List<CaseStatement> cases, @NotNull CodeBlock defaultBlock, @NotNull Node expression) {
        return visitScoped(ScopeType.SWITCH, () -> {
            Type expressionType = expression.accept(this).checkNot(Types.NO_TYPE, Types.NULL_TYPE);
            Type returnType = null;
            for (CaseStatement caseStatement : cases) {
                caseStatement.getExpression().accept(this).check(expressionType);
                Type caseType = caseStatement.accept(this);
                if (returnType == null) returnType = caseType;
                else if (!caseType.is(returnType)) returnType = Types.NO_TYPE;
            }
            Type defaultType = defaultBlock.accept(this);
            if (returnType == null) returnType = defaultType;
            else if (!defaultType.is(returnType)) returnType = Types.NO_TYPE;
            return returnType;
        });
    }

    @Override
    public @NotNull Type visitCaseStatement(@NotNull CodeBlock block, @NotNull Node expression) {
        return visitScoped(ScopeType.CASE, () -> {
            expression.accept(this);
            return block.accept(this);
        });
    }

    @Override
    public @NotNull Type visitDoStatement(@NotNull CodeBlock code, @NotNull Node expression) {
        return visitScoped(ScopeType.DO, () -> {
            expression.accept(this).check(BOOLEAN, ObjectType.BOOLEAN);
            return code.accept(this);
        });
    }

    @Override
    public @NotNull Type visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable, @NotNull CodeBlock code, @NotNull Node expression) {
        return visitScoped(ScopeType.FOR, () -> {
            ClassType variableType = type.accept(this).checkClassType();
            LiteralType variableName = variable.accept(this).check(LiteralType.class);
            this.environment.declare(variableType, variableName.getLiteral(), variableType.toType());

            Type expressionType = expression.accept(this);
            if (expressionType.is(ArrayType.class)) {
                Type componentType = expressionType.check(ArrayType.class).getComponentType();
                if (!componentType.isAssignableFrom(variableType))
                    throw TypeCheckerException.invalidType(componentType.toClassType(), variableType);
            } else {
                //TODO: Iterable generic should check for type
                ClassType iterable = ClassObjectType.of(Iterable.class);
                if (!expressionType.isAssignableFrom(iterable))
                    throw TypeCheckerException.invalidType(iterable, expressionType);
            }

            return code.accept(this);
        });
    }

    @Override
    public @NotNull Type visitForStatement(@NotNull Node assignment, @NotNull Node increment, @NotNull CodeBlock code, @NotNull Node expression) {
        return visitScoped(ScopeType.FOR, () -> {
            assignment.accept(this);
            expression.accept(this).check(BOOLEAN, ObjectType.BOOLEAN, Types.NO_TYPE);
            increment.accept(this);
            return code.accept(this);
        });
    }

    @Override
    public @NotNull Type visitIfStatement(@NotNull CodeBlock then, @NotNull Node elseBranch, @NotNull Node expression) {
        expression.accept(this).check(BOOLEAN, ObjectType.BOOLEAN);
        Type first = then.accept(this);
        Type second = elseBranch.accept(this);
        if (first.is(second) || elseBranch.equals(new Statement())) return first;
        else return Types.NO_TYPE;
    }

    @Override
    public @NotNull Type visitReturn(@NotNull Node expression) {
        return expression.accept(this);
    }

    @Override
    public @NotNull Type visitThrow(@NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Type visitStatement(@NotNull Node expression) {
        return expression.accept(this);
    }

    @Override
    public @NotNull Type visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expression) {
        return visitScoped(ScopeType.WHILE, () -> {
            expression.accept(this).check(BOOLEAN, ObjectType.BOOLEAN);
            return code.accept(this);
        });
    }

    @Override
    public @NotNull Type visitNullLiteral() {
        return Types.NULL_TYPE;
    }

    @Override
    public @NotNull Type visitThisLiteral() {
        return ObjectType.of(this.executingObject.getClass());
    }

    @Override
    public @NotNull Type visitBooleanValueLiteral(@NotNull String rawValue) {
        return BOOLEAN;
    }

    @Override
    public @NotNull Type visitCharValueLiteral(@NotNull String rawValue) {
        return CHAR;
    }

    @Override
    public @NotNull Type visitDoubleValueLiteral(@NotNull String rawValue) {
        return DOUBLE;
    }

    @Override
    public @NotNull Type visitFloatValueLiteral(@NotNull String rawValue) {
        return FLOAT;
    }

    @Override
    public @NotNull Type visitLongValueLiteral(@NotNull String rawValue) {
        return LONG;
    }

    @Override
    public @NotNull Type visitNumberValueLiteral(@NotNull String rawValue) {
        return NUMBER;
    }

    @Override
    public @NotNull Type visitStringValueLiteral(@NotNull String rawValue) {
        return STRING;
    }

    /**
     * Enters the specified {@link ScopeType}, executes the given function and
     * exits the scope.
     * If an exception is thrown:
     * <ul>
     *     <li>if its {@link RuntimeException}, it is thrown as is;</li>
     *     <li>otherwise, it is wrapped using {@link TypeCheckerException#of(Throwable)}.</li>
     * </ul>
     *
     * @param scope    the scope
     * @param function the function
     * @return the returned type by the function
     */
    @NotNull <T extends Type> T visitScoped(final @NotNull ScopeType scope,
                                            final @NotNull Callable<T> function) {
        try {
            this.environment.enterScope(scope);
            T type = function.call();
            this.environment.exitScope();
            return type;
        } catch (Exception e) {
            if (e instanceof RuntimeException) throw (RuntimeException) e;
            else throw TypeCheckerException.of(e);
        }
    }

    /**
     * Tries to convert the given literal to a {@link Type}.
     * It does so by first converting it to {@link ClassType}.
     * If it fails, it tries with a variable declared in {@link #environment}.
     *
     * @param literal the literal
     * @return if a {@link ClassType} is found, the tuple key and value will both be equal to the type itself. If a variable is found, the tuple key will have the type in which the variable was declared, while the value its actual value type. Otherwise, the tuple will be empty.
     */
    @NotNull Tuple<ClassType, Type> getTypeFromLiteral(final @NotNull String literal) {
        Tuple<ClassType, Type> tuple = new Tuple<>();
        try {
            ClassType type = ClassType.of(literal);
            tuple.set(type, type);
        } catch (TypeException e) {
            try {
                Type variable = this.environment.lookup(literal);
                ClassType variableType = (ClassType) this.environment.lookupInfo(literal);
                tuple.set(variableType, variable);
            } catch (ScopeException ignored) {}
        }
        return tuple;
    }

}
