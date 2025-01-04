package it.fulminazzo.javaparser.visitors.visitorobjects;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.javaparser.visitors.Visitor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * The object that will be returned by a {@link Visitor} instance.
 * Provides various useful methods for the visitor itself.
 *
 * @param <C> the type of the {@link ClassVisitorObject}
 * @param <O> the type of the {@link VisitorObject}
 */
public interface VisitorObject<C extends ClassVisitorObject<?, ?>, O extends VisitorObject<?, ?>> {

    /**
     * Checks whether the current object is a basic object of Java.
     *
     * @return true if it is
     */
    boolean isPrimitive();

    /**
     * Checks whether the current object is of the one specified.
     *
     * @param <V>    the type of the class
     * @param object the class of the object
     * @return true if it is
     */
    default <V extends O> boolean is(final @NotNull Class<V> object) {
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
     * Gets the given field from the associated {@link ClassVisitorObject} and returns it.
     *
     * @param fieldName the field name
     * @return the field
     */
    @NotNull Tuple<C, O> getField(final @NotNull String fieldName);

    /**
     * Searches and invokes the given method from the associated {@link ClassVisitorObject} and
     * returns the value returned from it.
     *
     * @param <V>        the type of the parameters
     * @param methodName the method name
     * @param parameters the parameters
     * @return the returned object from the method
     */
    <V extends O> @NotNull O invokeMethod(final @NotNull String methodName,
                                          final @NotNull ParameterVisitorObjects<V> parameters);

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
        BINARY COMPARISONS
     */

    /**
     * Executes and comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    @NotNull O and(final @NotNull O other);

    /**
     * Executes or comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    @NotNull O or(final @NotNull O other);

    /**
     * Executes equal comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    @NotNull O equal(final @NotNull O other);

    /**
     * Executes not equal comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    @NotNull O notEqual(final @NotNull O other);

    /**
     * Executes less than comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    @NotNull O lessThan(final @NotNull O other);

    /**
     * Executes less than equal comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    @NotNull O lessThanEqual(final @NotNull O other);

    /**
     * Executes greater than comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    @NotNull O greaterThan(final @NotNull O other);

    /**
     * Executes greater than equal comparison.
     *
     * @param other the other object
     * @return a boolean object
     */
    @NotNull O greaterThanEqual(final @NotNull O other);

    /*
        BINARY OPERATIONS
     */

    /**
     * Executes bit and operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    @NotNull O bitAnd(final @NotNull O other);

    /**
     * Executes bit or operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    @NotNull O bitOr(final @NotNull O other);

    /**
     * Executes bit xor operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    @NotNull O bitXor(final @NotNull O other);

    /**
     * Executes lshift operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    @NotNull O lshift(final @NotNull O other);

    /**
     * Executes rshift operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    @NotNull O rshift(final @NotNull O other);

    /**
     * Executes urshift operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    @NotNull O urshift(final @NotNull O other);


    /**
     * Executes add operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    @NotNull O add(final @NotNull O other);

    /**
     * Executes subtract operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    @NotNull O subtract(final @NotNull O other);

    /**
     * Executes multiply operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    @NotNull O multiply(final @NotNull O other);

    /**
     * Executes divide operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    @NotNull O divide(final @NotNull O other);

    /**
     * Executes modulo operation.
     *
     * @param other the other object
     * @return the resulting object
     */
    @NotNull O modulo(final @NotNull O other);

    /**
     * Executes minus operation.
     *
     * @return a boolean object
     */
    @NotNull O minus();

    /**
     * Executes not operation.
     *
     * @return a boolean object
     */
    @NotNull O not();

}
