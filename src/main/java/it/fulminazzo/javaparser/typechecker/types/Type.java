package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a general type parsed by the {@link it.fulminazzo.javaparser.typechecker.TypeChecker}.
 */
public interface Type {

    /**
     * Checks whether the current type is a basic value of Java (from {@link ValueType}).
     *
     * @return true if it is
     */
    default boolean isValue() {
        return is(ValueType.class);
    }

    /**
     * Checks whether the current type is a {@link ClassType}.
     *
     * @return true if it is
     */
    default boolean isClassType() {
        return is(ClassType.class);
    }

    /**
     * Checks whether the current type is of the one specified.
     *
     * @param <T>  the type
     * @param type the class of the type
     * @return true if it is
     */
    default <T extends Type> boolean is(final Class<T> type) {
        return type.isAssignableFrom(getClass());
    }

    /**
     * Checks whether the current type is equal to any of the ones given.
     *
     * @param types the types
     * @return true if it is for at least one of them
     */
    default boolean is(final Type @NotNull ... types) {
        return Arrays.asList(types).contains(this);
    }

    /**
     * Checks that the current type class is {@link ClassType}.
     * Throws {@link TypeCheckerException} in case it is not.
     *
     * @return the current type cast to the expected one
     */
    default @NotNull ClassType checkClassType() {
        return check(ClassType.class);
    }

    /**
     * Checks that the current type class is of the specified one.
     * Throws {@link TypeCheckerException} in case it is not.
     *
     * @param <T>       the class of the type
     * @param classType the class of the type
     * @return the current type cast to the expected one
     */
    default <T extends Type> @NotNull T check(final @NotNull Class<T> classType) {
        if (is(classType)) return classType.cast(this);
        else throw TypeCheckerException.invalidType(classType, this);
    }

    /**
     * Checks that the current type is of the specified one.
     * Throws {@link TypeCheckerException} in case it is not.
     *
     * @param <T>  the class of the type
     * @param type the expected type
     * @return the current type cast to the expected one
     */
    default <T extends Type> @NotNull T check(final @NotNull T type) {
        if (is(type)) return type;
        else throw TypeCheckerException.invalidType(type, this);
    }

    /**
     * Checks whether the current type is equal to one of the expected ones.
     * Throws a {@link TypeCheckerException} in case no match is found.
     *
     * @param expectedTypes the expected types
     * @return this type
     */
    default @NotNull Type check(final Type @NotNull ... expectedTypes) {
        if (expectedTypes.length == 0)
            throw new IllegalArgumentException(String.format("Cannot compare type %s with no types", this));
        for (Type expectedType : expectedTypes)
            if (is(expectedType)) return this;
        throw TypeCheckerException.invalidType(expectedTypes[0], this);
    }

    /**
     * Checks whether the current type is NOT equal to one of the expected ones.
     * Throws a {@link TypeCheckerException} in case no match is found.
     *
     * @param expectedTypes the expected types
     * @return this type
     */
    default @NotNull Type checkNot(final Type @NotNull ... expectedTypes) {
        if (expectedTypes.length == 0)
            throw new IllegalArgumentException(String.format("Cannot compare type %s with no types", this));
        for (Type expectedType : expectedTypes)
            if (is(expectedType)) throw TypeCheckerException.invalidUnexpectedType(this);
        return this;
    }

    /**
     * Converts the current object to its wrapper {@link ObjectType}.
     * This only works for {@link ValueType}s.
     *
     * @return the wrapper type
     */
    default @NotNull ObjectType toWrapper() {
        throw TypeCheckerException.noWrapper(this);
    }

    /**
     * Checks whether the current type extends the given {@link ClassType}.
     *
     * @param classType the class type
     * @return true if it is
     */
    default boolean isAssignableFrom(final @NotNull ClassType classType) {
        return classType.compatibleWith(this);
    }

    /**
     * Searches the given field in the class of {@link #toClassType()} (if not already {@link ClassType}).
     * Then, returns the declared type of the field in form of {@link ClassType}.
     *
     * @param fieldName the field name
     * @return the type of the field
     * @throws TypeException thrown in case the field could not be found or could not be accessed
     * (only <code>public</code> modifier allowed and <code>static</code> fields from static context)
     */
    default @NotNull ClassType getField(final @NotNull String fieldName) throws TypeException {
        ClassType classType = isClassType() ? (ClassType) this : toClassType();
        try {
            Class<?> javaClass = classType.toJavaClass();
            Field field = ReflectionUtils.getField(javaClass, fieldName);
            if (!Modifier.isPublic(field.getModifiers())) throw TypeException.cannotAccessField(classType, field);
            else if (isClassType() && !Modifier.isStatic(field.getModifiers()))
                throw TypeException.cannotAccessStaticField(classType, fieldName);
            return ClassType.of(field.getType());
        } catch (IllegalArgumentException e) {
            throw TypeException.fieldNotFound(classType, fieldName);
        }
    }

    /**
     * Searches the given method in the class of {@link #toClassType()} (if not already {@link ClassType}).
     * Then, returns the declared type of the method in form of {@link ClassType}.
     *
     * @param methodName the method name
     * @return the type of the method
     * @throws TypeException thrown in case the method could not be found, could not be accessed
     * (only <code>public</code> modifier allowed and <code>static</code> methods from static context)
     * or the given types did not match the expected ones
     */
    default @NotNull ClassType getMethod(final @NotNull String methodName,
                                         final @NotNull ParameterTypes parameterTypes) throws TypeException {
        if (isValue()) return toWrapper().getMethod(methodName, parameterTypes);
        ClassType classType = isClassType() ? (ClassType) this : toClassType();
        try {
            Class<?> javaClass = classType.toJavaClass();
            // Lookup methods from name and parameters count
            @NotNull List<Method> methods = ReflectionUtils.getMethods(javaClass, m ->
                    m.getName().equals(methodName) && TypeUtils.verifyExecutable(parameterTypes, m));
            if (methods.isEmpty()) throw new IllegalArgumentException();

            Refl<?> refl = new Refl<>(ReflectionUtils.class);
            Class<?> @NotNull [] parametersTypes = parameterTypes.toJavaClassArray();
            for (Method method : methods) {
                // For each one, validate its parameters
                if (Boolean.TRUE.equals(refl.invokeMethod("validateParameters",
                        new Class[]{Class[].class, Executable.class},
                        parametersTypes, method))) {
                    if (!Modifier.isPublic(method.getModifiers())) throw TypeException.cannotAccessMethod(classType, method);
                    else if (isClassType() && !Modifier.isStatic(method.getModifiers()))
                        throw TypeException.cannotAccessStaticMethod(classType, methodName, parameterTypes);
                    return ClassType.of(method.getReturnType());
                }
            }

            throw TypeException.typesMismatch(classType, methods.get(0), parameterTypes);
        } catch (IllegalArgumentException e) {
            throw TypeException.methodNotFound(classType, methodName, parameterTypes);
        }
    }

    /**
     * Converts the current object to its wrapper {@link ObjectType}.
     * This only works for {@link ValueType}s.
     *
     * @return the wrapper type
     */
    default @NotNull ObjectType toWrapper() {
        throw TypeCheckerException.noWrapper(this);
    }

    /**
     * Gets the class type associated with the current type.
     *
     * @return the class type
     */
    @NotNull ClassType toClassType();

    /**
     * Prints the given string to the format of a type.
     *
     * @param output the output
     * @return the new output
     */
    static @NotNull String print(final @NotNull String output) {
        return String.format("Type(%s)", output);
    }

}
