package it.fulminazzo.javaparser.visitors.visitorobjects;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import it.fulminazzo.javaparser.visitors.Visitor;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * The object that will be returned by a {@link Visitor} instance.
 * Provides various useful methods for the visitor itself.
 *
 * @param <C> the type of the {@link ClassVisitorObject}
 * @param <O> the type of the {@link VisitorObject}
 * @param <P> the type of the {@link ParameterVisitorObjects}
 */
@SuppressWarnings("unchecked")
public interface VisitorObject<
        C extends ClassVisitorObject<C, O, P>,
        O extends VisitorObject<C, O, P>,
        P extends ParameterVisitorObjects<C, O, P>
        > {

    /**
     * Checks whether the current object is a basic object of Java.
     *
     * @return true if it is
     */
    boolean isPrimitive();

    /**
     * Checks whether the current object is null.
     *
     * @return true if it is
     */
    boolean isNull();

    /**
     * Checks whether the current object is of the one specified.
     *
     * @param <V>    the type of the class
     * @param object the class of the object
     * @return true if it is
     */
    default <V extends VisitorObject<C, O, P>> boolean is(final @NotNull Class<V> object) {
        return object.isAssignableFrom(getClass());
    }

    /**
     * Checks whether the current object is equal to any of the ones given.
     *
     * @param objects the objects
     * @return true if it is for at least one of them
     */
    default boolean is(final O @NotNull ... objects) {
        return Arrays.asList(objects).contains(this);
    }

    /**
     * Checks whether the current object extends the given {@link ClassVisitorObject}.
     *
     * @param classVisitorObject the class object
     * @return true if it is
     */
    default boolean isAssignableFrom(final @NotNull ClassVisitorObject<C, O, P> classVisitorObject) {
        return classVisitorObject.compatibleWith(this);
    }

    /**
     * Checks that the current object is an instance of the specified one.
     *
     * @param <T>   the type of the class
     * @param clazz the class
     * @return this object cast to the given class
     */
    <T extends VisitorObject<C, O, P>> @NotNull T check(final @NotNull Class<T> clazz);

    /**
     * Checks that the current object is {@link ClassVisitorObject}.
     *
     * @return the current object cast to the expected one
     */
    @NotNull C checkClass();

    /**
     * Gets the given field from the associated {@link ClassVisitorObject} and returns it.
     *
     * @param fieldName the field name
     * @return the field
     * @throws VisitorObjectException the exception thrown in case of errors
     */
    @NotNull Tuple<C, O> getField(final @NotNull String fieldName) throws VisitorObjectException;

    /**
     * Searches and invokes the given method from the associated {@link ClassVisitorObject} and
     * returns the value returned from it.
     *
     * @param methodName the method name
     * @param parameters the parameters
     * @return the returned object from the method
     * @throws VisitorObjectException the exception thrown in case of errors
     */
    default @NotNull O invokeMethod(final @NotNull String methodName,
                                    final @NotNull P parameters) throws VisitorObjectException {
        if (isPrimitive()) return toWrapper().invokeMethod(methodName, parameters);
        C classVisitorObject = is(ClassVisitorObject.class) ? (C) this : toClass();
        try {
            Class<?> javaClass = classVisitorObject.toJavaClass();
            // Lookup methods from name and parameters count
            @NotNull List<Method> methods = ReflectionUtils.getMethods(javaClass, m ->
                    m.getName().equals(methodName) && VisitorObjectUtils.verifyExecutable(parameters, m));
            if (methods.isEmpty()) throw new IllegalArgumentException();

            Refl<?> refl = new Refl<>(ReflectionUtils.class);
            Class<?> @NotNull [] parametersTypes = parameters.stream()
                    //TODO: null
                    .map(o -> o.toClass())
                    .map(c -> c.toJavaClass())
                    .toArray(Class[]::new);

            for (Method method : methods) {
                // For each one, validate its parameters
                if (Boolean.TRUE.equals(refl.invokeMethod("validateParameters",
                        new Class[]{Class[].class, Executable.class},
                        parametersTypes, method)))
                    return invokeMethod(method, parameters);
            }

            throw typesMismatch(classVisitorObject, methods.get(0), parameters);
        } catch (IllegalArgumentException e) {
            throw methodNotFound(classVisitorObject, methodName, parameters);
        }
    }

    /**
     * Invokes the given method from the associated {@link ClassVisitorObject} and
     * returns the value returned from it.
     *
     * @param method     the method
     * @param parameters the parameters
     * @return the returned object from the method
     * @throws VisitorObjectException the exception thrown in case of errors
     */
    @NotNull O invokeMethod(final @NotNull Method method, final @NotNull P parameters) throws VisitorObjectException;

    /**
     * Converts the current object to its primitive associated object.
     *
     * @return the primitive object
     */
    @NotNull O toPrimitive();

    /**
     * Converts the current object to its wrapper associated object.
     *
     * @return the wrapper object
     */
    @NotNull O toWrapper();

    /**
     * Gets the class associated with the current object.
     *
     * @return the class object
     */
    @NotNull C toClass();

    /*
        EXCEPTIONS
     */

    /**
     * Generates a {@link VisitorObjectException} with message:
     * <i>Could not find method %method_format% in type %type%</i>
     *
     * @param classObject the class object
     * @param method      the method
     * @param parameters  the parameters
     * @return the visitor object exception
     */
    @NotNull VisitorObjectException methodNotFound(final @NotNull C classObject,
                                                   final @NotNull String method,
                                                   final @NotNull P parameters);

    /**
     * Generates a {@link VisitorObjectException} with message:
     * <i>Types mismatch: cannot apply parameters %parameter_types_format% to method %method_format% in type %type%</i>
     *
     * @param classObject the class object
     * @param method      the method
     * @param parameters  the parameters
     * @return the visitor object exception
     */
    @NotNull VisitorObjectException typesMismatch(final @NotNull C classObject,
                                                  final @NotNull Executable method,
                                                  final @NotNull P parameters);

    /**
     * Generates a {@link RuntimeException} with message:
     * <i>%clazz% does not have a class</i>
     *
     * @param type the type
     * @return the type runtime exception
     */
    @NotNull RuntimeException noClassType(final @NotNull Class<?> type);

    /**
     * Generates a {@link RuntimeException} with message:
     * <i>Operator '%operator%' cannot be applied to '%left%', '%right%'</i>
     *
     * @param operator the operator
     * @param left     the left operand
     * @param right    the right operand
     * @return the runtime exception
     */
    @NotNull RuntimeException unsupportedOperation(final @NotNull TokenType operator,
                                                   final @NotNull VisitorObject<C, O, P> left,
                                                   final @NotNull VisitorObject<C, O, P> right);

    /**
     * Generates a {@link RuntimeException} with message:
     * <i>Operator '%operator%' cannot be applied to '%operand%'</i>
     *
     * @param operator the operator
     * @param operand  the operand
     * @return the runtime exception
     */
    @NotNull RuntimeException unsupportedOperation(final @NotNull TokenType operator,
                                                   final @NotNull VisitorObject<C, O, P> operand);
    
    /*
        BINARY COMPARISONS
     */

    /**
     * Executes and comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    default @NotNull O and(final @NotNull O other) {
        throw unsupportedOperation(TokenType.AND, this, other);
    }

    /**
     * Executes or comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    default @NotNull O or(final @NotNull O other) {
        throw unsupportedOperation(TokenType.OR, this, other);
    }

    /**
     * Executes equal comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    default @NotNull O equal(final @NotNull O other) {
        throw unsupportedOperation(TokenType.EQUAL, this, other);
    }

    /**
     * Executes not equal comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    default @NotNull O notEqual(final @NotNull O other) {
        throw unsupportedOperation(TokenType.NOT_EQUAL, this, other);
    }

    /**
     * Executes less than comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    default @NotNull O lessThan(final @NotNull O other) {
        throw unsupportedOperation(TokenType.LESS_THAN, this, other);
    }

    /**
     * Executes less than equal comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    default @NotNull O lessThanEqual(final @NotNull O other) {
        throw unsupportedOperation(TokenType.LESS_THAN_EQUAL, this, other);
    }

    /**
     * Executes greater than comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    default @NotNull O greaterThan(final @NotNull O other) {
        throw unsupportedOperation(TokenType.GREATER_THAN, this, other);
    }

    /**
     * Executes greater than equal comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    default @NotNull O greaterThanEqual(final @NotNull O other) {
        throw unsupportedOperation(TokenType.GREATER_THAN_EQUAL, this, other);
    }

    /*
        BINARY OPERATIONS
     */

    /**
     * Executes bit and operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    default @NotNull O bitAnd(final @NotNull O other) {
        throw unsupportedOperation(TokenType.BIT_AND, this, other);
    }

    /**
     * Executes bit or operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    default @NotNull O bitOr(final @NotNull O other) {
        throw unsupportedOperation(TokenType.BIT_OR, this, other);
    }

    /**
     * Executes bit xor operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    default @NotNull O bitXor(final @NotNull O other) {
        throw unsupportedOperation(TokenType.BIT_XOR, this, other);
    }

    /**
     * Executes lshift operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    default @NotNull O lshift(final @NotNull O other) {
        throw unsupportedOperation(TokenType.LSHIFT, this, other);
    }

    /**
     * Executes rshift operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    default @NotNull O rshift(final @NotNull O other) {
        throw unsupportedOperation(TokenType.RSHIFT, this, other);
    }

    /**
     * Executes urshift operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    default @NotNull O urshift(final @NotNull O other) {
        throw unsupportedOperation(TokenType.URSHIFT, this, other);
    }


    /**
     * Executes add operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    default @NotNull O add(final @NotNull O other) {
        throw unsupportedOperation(TokenType.ADD, this, other);
    }

    /**
     * Executes subtract operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    default @NotNull O subtract(final @NotNull O other) {
        throw unsupportedOperation(TokenType.SUBTRACT, this, other);
    }

    /**
     * Executes multiply operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    default @NotNull O multiply(final @NotNull O other) {
        throw unsupportedOperation(TokenType.MULTIPLY, this, other);
    }

    /**
     * Executes divide operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    default @NotNull O divide(final @NotNull O other) {
        throw unsupportedOperation(TokenType.DIVIDE, this, other);
    }

    /**
     * Executes modulo operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    default @NotNull O modulo(final @NotNull O other) {
        throw unsupportedOperation(TokenType.MODULO, this, other);
    }

    /**
     * Executes minus operation.
     *
     * @return a boolean object
     */
    default @NotNull O minus() {
        throw unsupportedOperation(TokenType.SUBTRACT, this);
    }

    /**
     * Executes not operation.
     *
     * @return a boolean object
     */
    default @NotNull O not() {
        throw unsupportedOperation(TokenType.NOT, this);
    }

}
