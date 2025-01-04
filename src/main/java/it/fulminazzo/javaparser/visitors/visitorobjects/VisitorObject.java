package it.fulminazzo.javaparser.visitors.visitorobjects;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.javaparser.visitors.Visitor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * The object that will be returned by a {@link Visitor} instance.
 * Provides various useful methods for the visitor itself.
 */
public interface VisitorObject {

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
    default <V extends VisitorObject> boolean is(final @NotNull Class<V> object) {
        return object.isAssignableFrom(getClass());
    }

    /**
     * Checks whether the current object is equal to any of the ones given.
     *
     * @param objects the objects
     * @return true if it is for at least one of them
     */
    default boolean is(final VisitorObject @NotNull ... objects) {
        return Arrays.asList(objects).contains(this);
    }

    /**
     * Gets the given field from the associated {@link ClassVisitorObject} and returns it.
     *
     * @param fieldName the field name
     * @return the field
     */
    @NotNull Tuple<ClassVisitorObject, VisitorObject> getField(final @NotNull String fieldName);

    /**
     * Searches and invokes the given method from the associated {@link ClassVisitorObject} and
     * returns the value returned from it.
     *
     * @param <V>        the type of the parameters
     * @param methodName the method name
     * @param parameters the parameters
     * @return the returned object from the method
     */
    <V extends VisitorObject> @NotNull VisitorObject invokeMethod(final @NotNull String methodName,
                                                                  final @NotNull ParameterVisitorObjects<V> parameters);

    /**
     * Converts the current object to its primitive associated object.
     *
     * @return the primitive object
     */
    @NotNull VisitorObject toPrimitive();

    /**
     * Converts the current object to its wrapper associated object.
     *
     * @return the wrapper object
     */
    @NotNull VisitorObject toWrapper();

    /**
     * Gets the class associated with the current object.
     *
     * @return the class object
     */
    @NotNull ClassVisitorObject toClass();
    
    /*
        BINARY COMPARISONS
     */

    /**
     * Executes and comparison.
     *
     * @param other the other object
     * @return the boolean object
     */
    @NotNull VisitorObject and(final @NotNull VisitorObject other);

    /**
     * Executes or comparison.
     *
     * @param other the other object
     * @return the boolean object
     */
    @NotNull VisitorObject or(final @NotNull VisitorObject other);

    /**
     * Executes equal comparison.
     *
     * @param other the other object
     * @return the boolean object
     */
    @NotNull VisitorObject equal(final @NotNull VisitorObject other);

    /**
     * Executes not equal comparison.
     *
     * @param other the other object
     * @return the boolean object
     */
    @NotNull VisitorObject notEqual(final @NotNull VisitorObject other);

    /**
     * Executes less than comparison.
     *
     * @param other the other object
     * @return the boolean object
     */
    @NotNull VisitorObject lessThan(final @NotNull VisitorObject other);

    /**
     * Executes less than equal comparison.
     *
     * @param other the other object
     * @return the boolean object
     */
    @NotNull VisitorObject lessThanEqual(final @NotNull VisitorObject other);

    /**
     * Executes greater than comparison.
     *
     * @param other the other object
     * @return the boolean object
     */
    @NotNull VisitorObject greaterThan(final @NotNull VisitorObject other);

    /**
     * Executes greater than equal comparison.
     *
     * @param other the other object
     * @return the boolean object
     */
    @NotNull VisitorObject greaterThanEqual(final @NotNull VisitorObject other);

    /*
        BINARY OPERATIONS
     */

    /**
     * Executes bit and operation.
     *
     * @param other the other object
     * @return the object
     */
    @NotNull VisitorObject bitAnd(final @NotNull VisitorObject other);

    /**
     * Executes bit or operation.
     *
     * @param other the other object
     * @return the object
     */
    @NotNull VisitorObject bitOr(final @NotNull VisitorObject other);

    /**
     * Executes bit xor operation.
     *
     * @param other the other object
     * @return the object
     */
    @NotNull VisitorObject bitXor(final @NotNull VisitorObject other);

    /**
     * Executes lshift operation.
     *
     * @param other the other object
     * @return the object
     */
    @NotNull VisitorObject lshift(final @NotNull VisitorObject other);

    /**
     * Executes rshift operation.
     *
     * @param other the other object
     * @return the object
     */
    @NotNull VisitorObject rshift(final @NotNull VisitorObject other);

    /**
     * Executes urshift operation.
     *
     * @param other the other object
     * @return the object
     */
    @NotNull VisitorObject urshift(final @NotNull VisitorObject other);


    /**
     * Executes add operation.
     *
     * @param other the other object
     * @return the object
     */
    @NotNull VisitorObject add(final @NotNull VisitorObject other);

    /**
     * Executes subtract operation.
     *
     * @param other the other object
     * @return the object
     */
    @NotNull VisitorObject subtract(final @NotNull VisitorObject other);

    /**
     * Executes multiply operation.
     *
     * @param other the other object
     * @return the object
     */
    @NotNull VisitorObject multiply(final @NotNull VisitorObject other);

    /**
     * Executes divide operation.
     *
     * @param other the other object
     * @return the object
     */
    @NotNull VisitorObject divide(final @NotNull VisitorObject other);

    /**
     * Executes modulo operation.
     *
     * @param other the other object
     * @return the object
     */
    @NotNull VisitorObject modulo(final @NotNull VisitorObject other);

    /**
     * Executes minus operation.
     *
     * @return the boolean object
     */
    @NotNull VisitorObject minus();

    /**
     * Executes not operation.
     *
     * @return the boolean object
     */
    @NotNull VisitorObject not();

}
