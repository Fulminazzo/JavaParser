package it.fulminazzo.javaparser.executor;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.environment.NamedEntity;
import it.fulminazzo.javaparser.environment.ScopeException;
import it.fulminazzo.javaparser.environment.scopetypes.ScopeType;
import it.fulminazzo.javaparser.executor.values.*;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.arrays.ArrayClassValue;
import it.fulminazzo.javaparser.executor.values.arrays.ArrayValue;
import it.fulminazzo.javaparser.executor.values.objects.ObjectClassValue;
import it.fulminazzo.javaparser.executor.values.objects.ObjectValue;
import it.fulminazzo.javaparser.executor.values.primitivevalue.BooleanValue;
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import it.fulminazzo.javaparser.parser.node.statements.CaseStatement;
import it.fulminazzo.javaparser.parser.node.statements.CatchStatement;
import it.fulminazzo.javaparser.visitors.Visitor;
import it.fulminazzo.javaparser.visitors.visitorobjects.LiteralObject;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A {@link Visitor} that executes all the objects of the parsed code.
 */
@SuppressWarnings("unchecked")
@Getter
public class Executor implements Visitor<ClassValue<?>, Value<?>, ParameterValues> {
    private final @NotNull Object executingObject;
    private final @NotNull Environment<Value<?>> environment;

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
    public @NotNull Value<?> visitThrow(@NotNull Node expression) {
        Value<? extends Throwable> value = (Value<? extends Throwable>) expression.accept(this);
        throw new ExceptionWrapper(value);
    }

    @Override
    public @NotNull Value<?> visitBreak(@NotNull Node expression) {
        throw new BreakException();
    }

    @Override
    public @NotNull Value<?> visitContinue(@NotNull Node expression) {
        throw new ContinueException();
    }

    @Override
    public @NotNull Value<?> visitTryStatement(@NotNull CodeBlock block, @NotNull List<CatchStatement> catchBlocks,
                                               @NotNull CodeBlock finallyBlock, @NotNull Node assignments) {
        return visitScoped(ScopeType.TRY, () -> {
            assignments.accept(this);

            Map<ExceptionTuple, CodeBlock> exceptionsMap = new HashMap<>();
            for (CatchStatement catchStatement : catchBlocks) {
                TupleValue<List<ExceptionTuple>, CodeBlock> catchExceptions = (TupleValue<List<ExceptionTuple>, CodeBlock>)
                        catchStatement.accept(this);
                for (ExceptionTuple tuple : catchExceptions.getKey())
                    exceptionsMap.put(tuple, catchExceptions.getValue());
            }

            Value<?> returnedValue;
            try {
                returnedValue = block.accept(this);
            } catch (ExceptionWrapper e) {
                Value<? extends Throwable> exception = e.getActualException();
                Tuple<ExceptionTuple, CodeBlock> keyAndValue = getKeyAndValue(exceptionsMap, exception.toClass());
                returnedValue = visitScoped(ScopeType.CATCH, () -> {
                    ExceptionTuple key = keyAndValue.getKey();
                    this.environment.declare(key.getExceptionType(), key.getExceptionName(), exception);
                    return keyAndValue.getValue().accept(this);
                });
            } finally {
                Value<?> finalValue = finallyBlock.accept(this);
                if (!finalValue.is(Values.NO_VALUE)) returnedValue = finalValue;
            }
            return returnedValue;
        });
    }

    /**
     * Parses all the exceptions in a list of {@link ExceptionTuple}s
     * with the expression as associated name.
     * Then, returns a {@link TupleValue} containing the block as value.
     *
     * @param exceptions the exceptions
     * @param block      the block
     * @param expression the expression
     * @return the catch statement
     */
    @Override
    public @NotNull Value<?> visitCatchStatement(@NotNull List<Literal> exceptions, @NotNull CodeBlock block,
                                                 @NotNull Node expression) {
        List<ExceptionTuple> exceptionTuples = new ArrayList<>();
        for (Literal literal : exceptions)
            exceptionTuples.add(new ExceptionTuple(
                    literal.accept(this).checkClass(),
                    expression.accept(this).check(LiteralValue.class)
            ));
        return new TupleValue<>(exceptionTuples, block);
    }

    @Override
    public @NotNull Value<?> visitSwitchStatement(@NotNull List<CaseStatement> cases, @NotNull CodeBlock defaultBlock,
                                                  @NotNull Node expression) {
        return visitScoped(ScopeType.SWITCH, () -> {
            Value<?> compared = expression.accept(this);
            boolean switched = false;
            for (CaseStatement caseStatement : cases) {
                TupleValue<Node, CodeBlock> cs = (TupleValue<Node, CodeBlock>) caseStatement.accept(this);
                Value<?> comparison = cs.getKey().accept(this);
                if (compared.equal(comparison).equals(BooleanValue.TRUE) || switched)
                    try {
                        switched = true;
                        Value<?> returned = visitScoped(ScopeType.CASE, () -> cs.getValue().accept(this));
                        if (!returned.is(Values.NO_VALUE)) return returned;
                    } catch (BreakException ignored) {
                        return Values.NO_VALUE;
                    }
            }

            return defaultBlock.accept(this);
        });
    }

    /**
     * Converts the case statement to a {@link TupleValue} containing
     * the expression as key and block as value.
     * This will be later used by {@link #visitSwitchStatement(List, CodeBlock, Node)}.
     *
     * @param block      the block
     * @param expression the expression
     * @return the case statement
     */
    @Override
    public @NotNull Value<?> visitCaseStatement(@NotNull CodeBlock block, @NotNull Node expression) {
        return new TupleValue<>(expression, block);
    }

    @Override
    public @NotNull Value<?> visitForStatement(@NotNull Node assignment, @NotNull Node increment,
                                               @NotNull CodeBlock code, @NotNull Node expression) {
        return visitScoped(ScopeType.FOR, () -> {
            for (assignment.accept(this); expression.accept(this).is(BooleanValue.TRUE); increment.accept(this)) {
                Optional<Value<?>> returnedValue = visitLoopCodeBlock(code);
                if (returnedValue.isPresent()) return returnedValue.get();
            }
            return Values.NO_VALUE;
        });
    }

    @Override
    public @NotNull Value<?> visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable,
                                                       @NotNull CodeBlock code, @NotNull Node expression) {
        return visitScoped(ScopeType.FOR, () -> {
            ClassValue<?> variableType = type.accept(this).to(ClassValue.class);
            NamedEntity variableName = variable.accept(this).to(LiteralValue.class);
            Value<?> iterable = expression.accept(this);

            final Iterator<?> iterator;
            if (iterable.is(ArrayValue.class)) {
                ArrayValue<?> arrayValue = (ArrayValue<?>) iterable;
                iterator = Arrays.stream(arrayValue.getValue()).iterator();
            } else iterator = ((Iterable<?>) iterable.getValue()).iterator();

            while (iterator.hasNext()) {
                Value<?> next = Value.of(iterator.next());
                try {
                    this.environment.update(variableName, next);
                } catch (ScopeException ignored) {
                    this.environment.declare(variableType, variableName, next);
                }
                Optional<Value<?>> returnedValue = visitLoopCodeBlock(code);
                if (returnedValue.isPresent()) return returnedValue.get();
            }

            return Values.NO_VALUE;
        });
    }

    @Override
    public @NotNull Value<?> visitDoStatement(@NotNull CodeBlock code, @NotNull Node expression) {
        return visitScoped(ScopeType.DO, () -> {
            do {
                Optional<Value<?>> returnedValue = visitLoopCodeBlock(code);
                if (returnedValue.isPresent()) return returnedValue.get();
            } while (expression.accept(this).is(BooleanValue.TRUE));
            return Values.NO_VALUE;
        });
    }

    @Override
    public @NotNull Value<?> visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expression) {
        return visitScoped(ScopeType.WHILE, () -> {
            while (expression.accept(this).is(BooleanValue.TRUE)) {
                Optional<Value<?>> returnedValue = visitLoopCodeBlock(code);
                if (returnedValue.isPresent()) return returnedValue.get();
            }
            return Values.NO_VALUE;
        });
    }

    @Override
    public @NotNull Value<?> visitIfStatement(@NotNull CodeBlock then, @NotNull Node elseBranch, @NotNull Node expression) {
        if (expression.accept(this).is(BooleanValue.TRUE)) return then.accept(this);
        else return elseBranch.accept(this);
    }

    @Override
    public @NotNull Value<?> convertVariable(@NotNull ClassValue<?> variableType, @NotNull Value<?> variable) {
        // Test for uninitialized
        if (variable.is(visitEmptyLiteral()))
            variable = variableType.isPrimitive() ? variableType.toObject() : visitNullLiteral();
        if (!variable.isPrimitive()) return variable;
        else if (variableType.is(PrimitiveClassValue.class)) return variableType.cast(variable);
        else if (!variableType.is(ObjectClassValue.OBJECT)) // Can only be ClassObjectValue at this point
            return variableType.cast(variable);
        return variable;
    }

    @Override
    public @NotNull Value<?> visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type) {
        ClassValue<Object> componentsType = type.accept(this).to(ArrayClassValue.class).getComponentsType();
        Collection<Value<Object>> components = new LinkedList<>();
        for (Node component : parameters) components.add((Value<Object>) component.accept(this));
        return ArrayValue.of(componentsType, components);
    }

    @Override
    public @NotNull Value<?> visitStaticArray(int size, @NotNull Node type) {
        ClassValue<?> componentsType = type.accept(this).to(ClassValue.class);
        return ArrayValue.of(componentsType, size);
    }

    @Override
    public @NotNull Value<?> visitArrayLiteral(@NotNull Node type) {
        return ArrayClassValue.of(type.accept(this).to(ClassValue.class));
    }

    @Override
    public @NotNull ParameterValues visitMethodInvocation(@NotNull List<Node> parameters) {
        List<Value<?>> parameterValues = new LinkedList<>();
        for (Node parameter : parameters)
            parameterValues.add(parameter.accept(this));
        return new ParameterValues(parameterValues);
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
    public @NotNull Value<?> visitCharValueLiteral(@NotNull String rawValue) {
        return PrimitiveValue.of(rawValue.charAt(0));
    }

    @Override
    public @NotNull Value<?> visitNumberValueLiteral(@NotNull String rawValue) {
        return PrimitiveValue.of(Integer.parseInt(rawValue));
    }

    @Override
    public @NotNull Value<?> visitLongValueLiteral(@NotNull String rawValue) {
        return PrimitiveValue.of(Long.parseLong(rawValue));
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
    public @NotNull Value<?> visitBooleanValueLiteral(@NotNull String rawValue) {
        return PrimitiveValue.of(Boolean.parseBoolean(rawValue));
    }

    @Override
    public @NotNull Value<?> visitStringValueLiteral(@NotNull String rawValue) {
        return ObjectValue.of(rawValue);
    }

    @Override
    public @NotNull Tuple<ClassValue<?>, Value<?>> getObjectFromLiteral(@NotNull String literal) {
        try {
            Tuple<ClassValue<?>, Value<?>> tuple = new Tuple<>();
            if (literal.endsWith(".class")) {
                ClassValue<?> type = ClassValue.of(literal.substring(0, literal.length() - 6));
                tuple.set(type.toClass(), type.toClass());
            } else {
                ClassValue<?> type = ClassValue.of(literal);
                tuple.set(type, type);
            }
            return tuple;
        } catch (ValueException e) {
            return Visitor.super.getObjectFromLiteral(literal);
        }
    }

    @Override
    public @NotNull LiteralObject<ClassValue<?>, Value<?>, ParameterValues> newLiteralObject(@NotNull String value) {
        return new LiteralValue(value);
    }

    @Override
    public @NotNull Value<?> visitEmptyLiteral() {
        return Values.NO_VALUE;
    }

    /**
     * Searches the key in the given {@link Map}.
     * If found, returns a {@link Tuple} with the key and value found.
     * Otherwise, throws {@link IllegalArgumentException}.
     *
     * @param <K> the type of the key
     * @param <V> the type of the value
     * @param map the map
     * @param key the key
     * @return the tuple
     */
    <K, V> @NotNull Tuple<K, V> getKeyAndValue(final @NotNull Map<K, V> map,
                                               final @NotNull Object key) {
        for (Map.Entry<K, V> entry : map.entrySet())
            if (entry.getKey().equals(key))
                return new Tuple<>(entry.getKey(), entry.getValue());
        throw new IllegalArgumentException("Could not find key " + key);
    }

    /**
     * Support method for many break and continue supported statements.
     *
     * @param code the code
     * @return null in case nothing was returned, {@link Values#NO_VALUE} in case a {@link #visitBreak(Node)} occurred, otherwise the actual returned type of the codeblock
     */
    @NotNull Optional<Value<?>> visitLoopCodeBlock(final @NotNull CodeBlock code) {
        try {
            Value<?> returnedValue = code.accept(this);
            // Return occurred
            if (!returnedValue.is(Values.NO_VALUE)) return Optional.of(returnedValue);
        } catch (BreakException ignored) {
            return Optional.of(Values.NO_VALUE);
        } catch (ContinueException ignored) {
        }
        return Optional.empty();
    }

    @Override
    public @NotNull RuntimeException exceptionWrapper(@NotNull Exception exception) {
        return ExecutorException.of(exception);
    }

    @Override
    public @NotNull RuntimeException invalidType(@NotNull Class<?> expected, @NotNull Object actual) {
        return ExecutorException.invalidValue(expected, actual);
    }

    @Override
    public @NotNull RuntimeException cannotResolveSymbol(@NotNull String symbol) {
        return ExecutorException.cannotResolveSymbol(symbol);
    }

}
