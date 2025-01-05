package it.fulminazzo.javaparser.typechecker;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.environment.NamedEntity;
import it.fulminazzo.javaparser.environment.ScopeException;
import it.fulminazzo.javaparser.environment.scopetypes.ScopeType;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import it.fulminazzo.javaparser.parser.node.statements.CaseStatement;
import it.fulminazzo.javaparser.parser.node.statements.CatchStatement;
import it.fulminazzo.javaparser.typechecker.types.*;
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayClassType;
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayType;
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectClassType;
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType;
import it.fulminazzo.javaparser.visitors.Visitor;
import it.fulminazzo.javaparser.visitors.visitorobjects.LiteralObject;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static it.fulminazzo.javaparser.typechecker.types.PrimitiveType.*;

/**
 * A {@link Visitor} that checks and verifies all the types of the parsed code.
 */
@Getter
@SuppressWarnings("unchecked")
public class TypeChecker implements Visitor<ClassType, Type, ParameterTypes> {
    private static final ScopeType[] BREAK_SCOPES = new ScopeType[]{
            ScopeType.DO, ScopeType.WHILE, ScopeType.FOR, ScopeType.SWITCH, ScopeType.CASE
    };
    private static final ScopeType[] CONTINUE_SCOPES = new ScopeType[]{
            ScopeType.DO, ScopeType.WHILE, ScopeType.FOR
    };

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

    @Override
    public @NotNull Type visitThrow(@NotNull Node expression) {
        final ClassType throwable = ClassType.of(Throwable.class);
        final ClassType runtimeException = ClassType.of(RuntimeException.class);

        Type exceptionType = expression.accept(this).checkAssignableFrom(throwable);
        ClassType exception = exceptionType.toClass();
        if (!exceptionType.isAssignableFrom(runtimeException)) {
            if (!this.environment.isInTryScope((Class<? extends Throwable>) exception.toJavaClass()))
                throw TypeCheckerException.unhandledException(exception);
        }

        return Types.NO_TYPE;
    }

    @Override
    public @NotNull Type visitBreak(@NotNull Node expression) {
        try {
            this.environment.check(BREAK_SCOPES);
            return Types.NO_TYPE;
        } catch (ScopeException e) {
            throw TypeCheckerException.of(e);
        }
    }

    @Override
    public @NotNull Type visitContinue(@NotNull Node expression) {
        try {
            this.environment.check(CONTINUE_SCOPES);
            return Types.NO_TYPE;
        } catch (ScopeException e) {
            throw TypeCheckerException.of(e);
        }
    }

    @Override
    public @NotNull Type visitTryStatement(@NotNull CodeBlock block, @NotNull List<CatchStatement> catchBlocks,
                                           @NotNull CodeBlock finallyBlock, @NotNull Node expression) {
        return visitScoped(ScopeType.TRY, () -> {
            final ClassType autoClosable = ClassType.of(AutoCloseable.class);

            ParameterTypes assignments = expression.accept(this).check(ParameterTypes.class);
            for (Type assignment : assignments) ((ClassType) assignment).checkExtends(autoClosable);

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
        return (TupleType<Set<ClassType>, Type>) visitScoped(ScopeType.CATCH, () -> {
            final ClassType throwable = ClassType.of(Throwable.class);

            final List<ClassType> exceptionTypes = new LinkedList<>();
            for (Literal exception : exceptions) {
                ClassType exceptionClass = exception.accept(this).checkClass();
                Type exceptionType = exceptionClass.toType().checkAssignableFrom(throwable);

                if (exceptionTypes.contains(exceptionClass))
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
                @NotNull NamedEntity exceptionName = NamedEntity.of(literal.getLiteral());

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
    public @NotNull Type visitForStatement(@NotNull Node assignment, @NotNull Node increment,
                                           @NotNull CodeBlock code, @NotNull Node expression) {
        return visitScoped(ScopeType.FOR, () -> {
            assignment.accept(this);
            expression.accept(this).check(BOOLEAN, ObjectType.BOOLEAN, Types.NO_TYPE);
            increment.accept(this);
            return code.accept(this);
        });
    }

    @Override
    public @NotNull Type visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable,
                                                   @NotNull CodeBlock code, @NotNull Node expression) {
        return visitScoped(ScopeType.FOR, () -> {
            ClassType variableType = type.accept(this).checkClass();
            LiteralType variableName = variable.accept(this).check(LiteralType.class);
            this.environment.declare(variableType, NamedEntity.of(variableName.getName()), variableType.toType());

            Type expressionType = expression.accept(this);
            if (expressionType.is(ArrayType.class)) {
                Type componentType = expressionType.check(ArrayType.class).getComponentType();
                if (!componentType.isAssignableFrom(variableType))
                    throw TypeCheckerException.invalidType(componentType.toClass(), variableType);
            } else {
                //TODO: Iterable generic should check for type
                ClassType iterable = ObjectClassType.of(Iterable.class);
                expressionType.checkAssignableFrom(iterable);
            }

            return code.accept(this);
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
    public @NotNull Type visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expression) {
        return visitScoped(ScopeType.WHILE, () -> {
            expression.accept(this).check(BOOLEAN, ObjectType.BOOLEAN);
            return code.accept(this);
        });
    }

    @Override
    public @NotNull Type visitIfStatement(@NotNull CodeBlock then, @NotNull Node elseBranch, @NotNull Node expression) {
        expression.accept(this).check(BOOLEAN, ObjectType.BOOLEAN);
        Type first = then.accept(this);
        Type second = elseBranch.accept(this);
        // it is impossible to know if, when the elseBranch is declared empty, the then block was invoked
        return first.is(second) ? first : Types.NO_TYPE;
    }

    @Override
    public @NotNull Type convertVariable(@NotNull ClassType variableType, @NotNull Type variable) {
        variable = convertByteAndShort(variableType, variable).checkAssignableFrom(variableType);
        if (!variable.isPrimitive()) return variable;
        else if (variableType.is(PrimitiveClassType.class)) return variableType.toType();
        else if (!variableType.is(ObjectClassType.OBJECT)) // Can only be ClassObjectType at this point
            return variableType.toType();
        return variable;
    }

    /**
     * Support function for {@link #visitAssignment(Node, Literal, Node)}
     * and {@link #visitReAssign(Node, Node)}.
     * Converts {@link PrimitiveType}s for {@link PrimitiveClassType#BYTE} and {@link PrimitiveClassType#SHORT}.
     *
     * @param variableType  the variable type
     * @param variableValue the variable value
     * @return the type
     */
    @NotNull Type convertByteAndShort(final @NotNull ClassType variableType,
                                      final @NotNull Type variableValue) {
        if (variableType.is(PrimitiveClassType.BYTE, ObjectClassType.BYTE, PrimitiveClassType.SHORT, ObjectClassType.SHORT))
            if (variableValue.is(INT) || variableValue.is(CHAR)) return variableType.toType();
        return variableValue;
    }

    @Override
    public @NotNull Type visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type) {
        ClassType componentType = type.accept(this).check(ArrayClassType.class).getComponentType();
        for (Node parameter : parameters) parameter.accept(this).isAssignableFrom(componentType);
        return new ArrayType(componentType.toType());
    }

    @Override
    public @NotNull Type visitStaticArray(int size, @NotNull Node type) {
        ClassType componentType = type.accept(this).checkClass();
        if (size < 0) throw TypeCheckerException.invalidArraySize(size);
        else return new ArrayType(componentType.toType());
    }

    @Override
    public @NotNull Type visitArrayLiteral(@NotNull Node type) {
        return new ArrayClassType(type.accept(this).checkClass());
    }

    @Override
    public @NotNull ParameterTypes visitMethodInvocation(@NotNull List<Node> parameters) {
        List<Type> parameterTypes = new LinkedList<>();
        for (Node parameter : parameters)
            parameterTypes.add(parameter.accept(this));
        return new ParameterTypes(parameterTypes);
    }

    @Override
    public @NotNull Type visitNullLiteral() {
        return Types.NULL_TYPE;
    }

    @Override
    public @NotNull Type visitThisLiteral() {
        return ObjectType.of(getExecutingObject().getClass());
    }

    @Override
    public @NotNull Type visitCharValueLiteral(@NotNull String rawValue) {
        return PrimitiveType.CHAR;
    }

    @Override
    public @NotNull Type visitNumberValueLiteral(@NotNull String rawValue) {
        return PrimitiveType.INT;
    }

    @Override
    public @NotNull Type visitLongValueLiteral(@NotNull String rawValue) {
        return PrimitiveType.LONG;
    }

    @Override
    public @NotNull Type visitDoubleValueLiteral(@NotNull String rawValue) {
        return PrimitiveType.DOUBLE;
    }

    @Override
    public @NotNull Type visitFloatValueLiteral(@NotNull String rawValue) {
        return PrimitiveType.FLOAT;
    }

    @Override
    public @NotNull Type visitBooleanValueLiteral(@NotNull String rawValue) {
        return PrimitiveType.BOOLEAN;
    }

    @Override
    public @NotNull Type visitStringValueLiteral(@NotNull String rawValue) {
        return ObjectType.STRING;
    }

    @Override
    public @NotNull LiteralObject<ClassType, Type, ParameterTypes> newLiteralObject(@NotNull String value) {
        return new LiteralType(value);
    }

    @Override
    public @NotNull Tuple<ClassType, Type> getObjectFromLiteral(@NotNull String literal) {
        Tuple<ClassType, Type> tuple = new Tuple<>();
        try {
            if (literal.endsWith(".class")) {
                ClassType type = ClassType.of(literal.substring(0, literal.length() - 6));
                tuple.set(type.toClass(), type.toClass());
            } else {
                ClassType type = ClassType.of(literal);
                tuple.set(type, type);
            }
            return tuple;
        } catch (TypeException e) {
            return Visitor.super.getObjectFromLiteral(literal);
        }
    }

    @Override
    public @NotNull Type visitEmptyLiteral() {
        return Types.NO_TYPE;
    }

    @Override
    public @NotNull RuntimeException exceptionWrapper(@NotNull Exception exception) {
        return TypeCheckerException.of(exception);
    }

    @Override
    public @NotNull RuntimeException invalidType(@NotNull Class<?> expected, @NotNull Object actual) {
        return TypeCheckerException.invalidType(expected, actual);
    }

    @Override
    public @NotNull RuntimeException cannotResolveSymbol(@NotNull String symbol) {
        return TypeCheckerException.cannotResolveSymbol(symbol);
    }

}
