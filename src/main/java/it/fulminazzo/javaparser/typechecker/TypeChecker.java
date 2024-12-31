package it.fulminazzo.javaparser.typechecker;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.environment.ScopeException;
import it.fulminazzo.javaparser.parser.node.MethodInvocation;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.parser.node.container.JavaProgram;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import it.fulminazzo.javaparser.parser.node.statements.Return;
import it.fulminazzo.javaparser.parser.node.statements.Statement;
import it.fulminazzo.javaparser.typechecker.types.*;
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayClassType;
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayType;
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType;
import it.fulminazzo.javaparser.visitors.Visitor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static it.fulminazzo.javaparser.typechecker.OperationUtils.*;
import static it.fulminazzo.javaparser.typechecker.types.ValueType.*;

/**
 * A {@link Visitor} that checks and verifies all the types of the parsed code.
 */
public final class TypeChecker implements Visitor<Type> {
    /**
     * The constant FIELDS_SEPARATOR.
     */
    public static final String FIELDS_SEPARATOR = ".";
    private final Class<?> executingClass;
    private final Environment<Type> environment;

    /**
     * Instantiates a new Type checker.
     */
    public TypeChecker(final @NotNull Class<?> executingClass) {
        this.executingClass = executingClass;
        this.environment = new Environment<>();
    }

    /**
     * Starting point of the {@link Visitor}.
     * It reads the given {@link JavaProgram} using all the methods in this class.
     *
     * @param program the program
     * @return an {@link Optional} with the returned type if it is not equal to {@link NoType#NO_TYPE}
     */
    @Override
    public @NotNull Optional<Type> visitProgram(@NotNull JavaProgram program) {
        Type type = program.accept(this);
        return type.equals(NoType.NO_TYPE) ? Optional.empty() : Optional.of(type);
    }

    @Override
    public @NotNull Type visitAssignment(@NotNull Node type, @NotNull Literal name, @NotNull Node value) {
        ClassType variableType = type.accept(this).checkClassType();
        Type tempVariableName = name.accept(this);
        if (!tempVariableName.is(LiteralType.class))
            throw TypeCheckerException.of(this.environment.alreadyDeclaredVariable(name.getLiteral()));
        LiteralType variableName = tempVariableName.check(LiteralType.class);
        Type variableValue = convertByteAndShort(variableType, value.accept(this));
        if (variableValue.isAssignableFrom(variableType)) {
            try {
                this.environment.declare(variableType, variableName.getLiteral(), convertValue(variableType, variableValue));
            } catch (ScopeException ignored) {}
            return NoType.NO_TYPE;
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
    public @NotNull Type visitMethodCall(@NotNull Node executor, @NotNull MethodInvocation invocation) {
        if (executor.is(Literal.class))
            try {
                String literal = ((Literal) executor).getLiteral();
                final @NotNull Type executorType;
                final @NotNull String methodName;
                if (literal.contains(FIELDS_SEPARATOR)) {
                    methodName = literal.substring(literal.indexOf(FIELDS_SEPARATOR) + 1);
                    literal = literal.substring(0, literal.indexOf(FIELDS_SEPARATOR));
                    executorType = visitLiteralImpl(literal);
                } else {
                    executorType = ClassType.of(this.executingClass);
                    methodName = literal;
                }
                return executorType.getMethod(methodName, (ParameterTypes) invocation.accept(this)).toType();
            } catch (TypeException e) {
                throw TypeCheckerException.of(e);
            }
        else throw new IllegalStateException("Not implemented");
    }

    @Override
    public @NotNull Type visitMethodInvocation(@NotNull List<Node> parameters) {
        List<ClassType> parameterTypes = new LinkedList<>();
        for (Node parameter : parameters)
            parameterTypes.add(parameter.accept(this).checkClassType());
        return new ParameterTypes(parameterTypes);
    }

    @Override
    public @NotNull Type visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type) {
        ClassType componentType = type.accept(this).checkClassType();
        for (Node parameter : parameters) parameter.accept(this).isAssignableFrom(componentType);
        return new ArrayType(componentType);
    }

    @Override
    public @NotNull Type visitStaticArray(int size, @NotNull Node type) {
        Type componentType = type.accept(this).checkClassType();
        if (size < 0) throw TypeCheckerException.invalidArraySize(size);
        else return new ArrayType(componentType);
    }

    @Override
    public @NotNull Type visitCodeBlock(@NotNull LinkedList<Statement> statements) {
        Type type = NoType.NO_TYPE;
        for (Statement statement : statements) {
            Type checked = statement.accept(this);
            if (statement.is(Return.class)) type = checked;
        }
        return type;
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
        return NoType.NO_TYPE;
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
        return null;
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
    public @NotNull Type visitBreak(@NotNull Node expr) {
        return NoType.NO_TYPE;
    }

    @Override
    public @NotNull Type visitContinue(@NotNull Node expr) {
        return NoType.NO_TYPE;
    }

    @Override
    public @NotNull Type visitDoStatement(@NotNull CodeBlock code, @NotNull Node expr) {
        return null;
    }

    @Override
    public @NotNull Type visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable, @NotNull CodeBlock code, @NotNull Node expr) {
        return null;
    }

    @Override
    public @NotNull Type visitForStatement(@NotNull Node assignment, @NotNull Node increment, @NotNull CodeBlock code, @NotNull Node expr) {
        return null;
    }

    @Override
    public @NotNull Type visitIfStatement(@NotNull CodeBlock code, @NotNull Node thenBranch, @NotNull Node expr) {
        return null;
    }

    @Override
    public @NotNull Type visitReturn(@NotNull Node expr) {
        return expr.accept(this);
    }

    @Override
    public @NotNull Type visitStatement(@NotNull Node expr) {
        return null;
    }

    @Override
    public @NotNull Type visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expr) {
        return null;
    }

    @Override
    public @NotNull Type visitNullLiteral() {
        return null;
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
     * Tries to convert the given literal to a {@link Type}.
     * It does so by first converting it to {@link ClassType}.
     * If it fails, it tries with a variable declared in {@link #environment}.
     *
     * @param literal the literal
     * @return if a {@link ClassType} is found, the tuple key and value will both be
     * equal to the type itself. If a variable is found, the tuple key will have the
     * type in which the variable was declared, while the value its actual value type.
     * Otherwise, the tuple will be empty.
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
