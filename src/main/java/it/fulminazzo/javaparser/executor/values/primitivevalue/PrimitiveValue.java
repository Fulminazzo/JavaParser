package it.fulminazzo.javaparser.executor.values.primitivevalue;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.executor.values.*;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a primitive {@link Value}.
 *
 * @param <V> the type of the value
 */
public abstract class PrimitiveValue<V> extends ObjectWrapper<V> implements Value<V> {

    /**
     * Instantiates a new Primitive value.
     *
     * @param value the value
     */
    PrimitiveValue(@NotNull V value) {
        super(value);
    }

    @Override
    public boolean isCharacter() {
        return is(CharValue.class);
    }

    @Override
    public boolean isInteger() {
        return is(IntValue.class);
    }

    @Override
    public boolean isLong() {
        return is(LongValue.class);
    }

    @Override
    public boolean isFloat() {
        return is(FloatValue.class);
    }

    @Override
    public boolean isDouble() {
        return is(DoubleValue.class);
    }

    @Override
    public boolean isBoolean() {
        return is(BooleanValue.class);
    }

    @Override
    public @NotNull PrimitiveValue<V> toPrimitive() {
        return this;
    }

    @Override
    public @NotNull V getValue() {
        return this.object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull ClassValue<V> toClassValue() {
        Class<?> clazz = ReflectionUtils.getPrimitiveClass(getValue().getClass());
        return (ClassValue<V>) PrimitiveClassValue.valueOf(clazz.getSimpleName().toUpperCase());
    }

    /**
     * Gets the most appropriate {@link PrimitiveValue} from the given value.
     * Throws {@link ValueRuntimeException} in case of an invalid value.
     *
     * @param <V>   the type of the value
     * @param value the value
     * @return the primitive value
     */
    @SuppressWarnings("unchecked")
    public static <V> @NotNull PrimitiveValue<V> of(@NotNull V value) {
        Value<?> primitiveValue;
        if (value instanceof Double) primitiveValue = new DoubleValue((Double) value);
        else if (value instanceof Float) primitiveValue = new FloatValue((Float) value);
        else if (value instanceof Long) primitiveValue = new LongValue((Long) value);
        else if (value instanceof Boolean)
            if (Boolean.TRUE.equals(value)) primitiveValue = BooleanValue.TRUE;
            else primitiveValue = BooleanValue.FALSE;
        else if (value instanceof Character) primitiveValue = new CharValue((Character) value);
        else if (value instanceof Byte) primitiveValue = new ByteValue(Byte.parseByte(value.toString()));
        else if (value instanceof Short) primitiveValue = new ShortValue(Short.parseShort(value.toString()));
        else if (value instanceof Integer) primitiveValue = new IntValue(Integer.parseInt(value.toString()));
        else throw ValueRuntimeException.invalidPrimitiveValue(value);
        return (PrimitiveValue<V>) primitiveValue;
    }

    /*
        To prevent StackOverflowErrors, all operations are overridden.
     */

    @Override
    public @NotNull BooleanValue and(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.AND, this, other);
    }

    @Override
    public @NotNull BooleanValue or(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.OR, this, other);
    }

    @Override
    public @NotNull BooleanValue lessThan(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.LESS_THAN, this, other);
    }

    @Override
    public @NotNull BooleanValue lessThanEqual(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.LESS_THAN_EQUAL, this, other);
    }

    @Override
    public @NotNull BooleanValue greaterThan(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.GREATER_THAN, this, other);
    }

    @Override
    public @NotNull BooleanValue greaterThanEqual(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.GREATER_THAN_EQUAL, this, other);
    }

    @Override
    public @NotNull Value<?> bitAnd(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.BIT_AND, this, other);
    }

    @Override
    public @NotNull Value<?> bitOr(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.BIT_OR, this, other);
    }

    @Override
    public @NotNull Value<?> bitXor(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.BIT_XOR, this, other);
    }

    @Override
    public @NotNull Value<?> lshift(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.LSHIFT, this, other);
    }

    @Override
    public @NotNull Value<?> rshift(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.RSHIFT, this, other);
    }

    @Override
    public @NotNull Value<?> urshift(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.URSHIFT, this, other);
    }

    @Override
    public @NotNull Value<?> add(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.ADD, this, other);
    }

    @Override
    public @NotNull Value<?> subtract(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.SUBTRACT, this, other);
    }

    @Override
    public @NotNull Value<?> multiply(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.MULTIPLY, this, other);
    }

    @Override
    public @NotNull Value<?> divide(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.DIVIDE, this, other);
    }

    @Override
    public @NotNull Value<?> modulo(@NotNull Value<?> other) {
        throw ValueRuntimeException.unsupportedOperation(TokenType.MODULO, this, other);
    }

    @Override
    public @NotNull Value<?> minus() {
        throw ValueRuntimeException.unsupportedOperation(TokenType.SUBTRACT, this);
    }

    @Override
    public @NotNull BooleanValue not() {
        throw ValueRuntimeException.unsupportedOperation(TokenType.NOT, this);
    }

}
