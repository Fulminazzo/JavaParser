package it.fulminazzo.javaparser.executor;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.environment.ScopeException;
import it.fulminazzo.javaparser.executor.values.*;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.objects.ObjectValue;
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue;
import it.fulminazzo.javaparser.parser.node.Assignment;
import it.fulminazzo.javaparser.parser.node.MethodInvocation;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.parser.node.container.JavaProgram;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import it.fulminazzo.javaparser.parser.node.statements.CaseStatement;
import it.fulminazzo.javaparser.parser.node.statements.CatchStatement;
import it.fulminazzo.javaparser.parser.node.statements.Statement;
import it.fulminazzo.javaparser.visitors.Visitor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * A {@link Visitor} that executes all the objects of the parsed code.
 */
@SuppressWarnings("unchecked")
public class Executor implements Visitor<Value<?>> {
    private static final String FIELDS_SEPARATOR = ".";

    private final Object executingObject;
    private final Environment<Value<?>> environment;

    /**
     * Instantiates a new Executor.
     *
     * @param executingObject the executing object
     */
    public Executor(final @NotNull Object executingObject) {
        this.executingObject = executingObject;
        this.environment = new Environment<>();
    }

    @Override
    public @NotNull Optional<Value<?>> visitProgram(@NotNull JavaProgram program) {
        return Optional.empty();
    }

    @Override
    public @NotNull Value<?> visitAssignmentBlock(@NotNull List<Assignment> assignments) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitAssignment(@NotNull Node type, @NotNull Literal name, @NotNull Node value) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitMethodCall(@NotNull Node executor, @NotNull String methodName, @NotNull MethodInvocation invocation) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitField(@NotNull Node left, @NotNull Node right) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitMethodInvocation(@NotNull List<Node> parameters) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitStaticArray(int size, @NotNull Node type) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitCodeBlock(@NotNull LinkedList<Statement> statements) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitJavaProgram(@NotNull LinkedList<Statement> statements) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitArrayLiteral(@NotNull Node type) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitEmptyLiteral() {
        return null;
    }

    @Override
    public @NotNull Value<?> visitLiteralImpl(@NotNull String value) {
        Tuple<ClassValue<Object>, Value<Object>> tuple = getValueFromLiteral(value);
        // Class was parsed
        if (tuple.isPresent()) return tuple.getValue();
        else if (value.contains(FIELDS_SEPARATOR)) {
            LinkedList<String> first = new LinkedList<>(Arrays.asList(value.split("\\" + FIELDS_SEPARATOR)));
            LinkedList<String> last = new LinkedList<>();

            while (!first.isEmpty()) {
                last.addFirst(first.removeLast());

                tuple = getValueFromLiteral(String.join(".", first));
                if (tuple.isPresent()) {
                    Tuple<ClassValue<Object>, Value<Object>> field = tuple.copy();
                    do field = field.getValue().getField(last.removeFirst());
                    while (!last.isEmpty());
                    return field.getValue();
                }
            }
            throw ExecutorException.cannotResolveSymbol(value);
        } else return new LiteralValue(value);
    }

    @Override
    public @NotNull Value<?> visitAdd(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).add(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitAnd(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).and(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitBitAnd(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).bitAnd(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitBitOr(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).bitOr(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitBitXor(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).bitXor(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitCast(@NotNull Node left, @NotNull Node right) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitDivide(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).divide(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitEqual(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).equal(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitGreaterThan(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).greaterThan(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitGreaterThanEqual(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).greaterThanEqual(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitLShift(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).lshift(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitLessThan(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).lessThan(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitLessThanEqual(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).lessThanEqual(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitModulo(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).modulo(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitMultiply(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).multiply(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitNewObject(@NotNull Node left, @NotNull Node right) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitNotEqual(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).notEqual(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitOr(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).or(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitRShift(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).rshift(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitReAssign(@NotNull Node left, @NotNull Node right) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitSubtract(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).subtract(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitURShift(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).urshift(right.accept(this));
    }

    @Override
    public @NotNull Value<?> visitDecrement(boolean before, @NotNull Node operand) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitIncrement(boolean before, @NotNull Node operand) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitMinus(@NotNull Node operand) {
        return operand.accept(this).minus();
    }

    @Override
    public @NotNull Value<?> visitNot(@NotNull Node operand) {
        return operand.accept(this).not();
    }

    @Override
    public @NotNull Value<?> visitBreak(@NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitContinue(@NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitTryStatement(@NotNull CodeBlock block, @NotNull List<CatchStatement> catchBlocks, @NotNull CodeBlock finallyBlock, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitCatchStatement(@NotNull List<Literal> exceptions, @NotNull CodeBlock block, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitSwitchStatement(@NotNull List<CaseStatement> cases, @NotNull CodeBlock defaultBlock, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitCaseStatement(@NotNull CodeBlock block, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitDoStatement(@NotNull CodeBlock code, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable, @NotNull CodeBlock code, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitForStatement(@NotNull Node assignment, @NotNull Node increment, @NotNull CodeBlock code, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitIfStatement(@NotNull CodeBlock then, @NotNull Node elseBranch, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitReturn(@NotNull Node expression) {
        return expression.accept(this);
    }

    @Override
    public @NotNull Value<?> visitThrow(@NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitStatement(@NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Value<?> visitNullLiteral() {
        return Values.NULL_VALUE;
    }

    @Override
    public @NotNull Value<?> visitThisLiteral() {
        return ObjectValue.of(this.executingObject);
    }

    @Override
    public @NotNull Value<?> visitBooleanValueLiteral(@NotNull String rawValue) {
        return PrimitiveValue.of(Boolean.parseBoolean(rawValue));
    }

    @Override
    public @NotNull Value<?> visitCharValueLiteral(@NotNull String rawValue) {
        return PrimitiveValue.of(rawValue.charAt(0));
    }

    @Override
    public @NotNull Value<?> visitDoubleValueLiteral(@NotNull String rawValue) {
        return PrimitiveValue.of(Double.valueOf(rawValue));
    }

    @Override
    public @NotNull Value<?> visitFloatValueLiteral(@NotNull String rawValue) {
        return PrimitiveValue.of(Float.parseFloat(rawValue));
    }

    @Override
    public @NotNull Value<?> visitLongValueLiteral(@NotNull String rawValue) {
        return PrimitiveValue.of(Long.parseLong(rawValue));
    }

    @Override
    public @NotNull Value<?> visitNumberValueLiteral(@NotNull String rawValue) {
        return PrimitiveValue.of(Integer.parseInt(rawValue));
    }

    @Override
    public @NotNull Value<?> visitStringValueLiteral(@NotNull String rawValue) {
        return ObjectValue.of(rawValue);
    }

    /**
     * Tries to convert the given literal to a {@link Value}.
     * It does so by first converting it to {@link ClassValue}.
     * If it fails, it tries with a variable declared in {@link #environment}.
     *
     * @param literal the literal
     * @return if a {@link ClassValue} is found, the tuple key and value will both be equal to the value itself.
     * If a variable is found, the tuple key will have the value in which the variable was declared, 
     * while the value its actual value.
     * Otherwise, the tuple will be empty.
     */
    <V> @NotNull Tuple<ClassValue<V>, Value<V>> getValueFromLiteral(final @NotNull String literal) {
        Tuple<ClassValue<V>, Value<V>> tuple = new Tuple<>();
        try {
            ClassValue<V> value = ClassValue.of(literal);
            tuple.set(value, (Value<V>) value);
        } catch (ValueException e) {
            try {
                Value<V> variable = (Value<V>) this.environment.lookup(literal);
                ClassValue<V> variableValue = (ClassValue<V>) this.environment.lookupInfo(literal);
                tuple.set(variableValue, variable);
            } catch (ScopeException ignored) {
            }
        }
        return tuple;
    }

}
