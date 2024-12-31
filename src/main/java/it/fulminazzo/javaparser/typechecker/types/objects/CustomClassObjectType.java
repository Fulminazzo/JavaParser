package it.fulminazzo.javaparser.typechecker.types.objects;

import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.TypeWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link ClassObjectType} with a class different from the default types.
 */
class CustomClassObjectType extends TypeWrapper implements ClassType {

    /**
     * Instantiates a new Custom class object type.
     *
     * @param internalType the internal type
     */
    public CustomClassObjectType(@NotNull ObjectType internalType) {
        super(internalType);
    }

    @Override
    public @NotNull Type toType() {
        return this.object;
    }

    @Override
    public @NotNull Type cast(@NotNull Type type) {
        if (type.is(ObjectType.class)) {
            Class<?> typeClass = ((ObjectType) type).getInnerClass();
            Class<?> currentClass = toJavaClass();
            if (currentClass.isAssignableFrom(typeClass) || typeClass.isAssignableFrom(currentClass))
                return toType();
        }
        throw TypeCheckerException.invalidCast(this, type);
    }

    @Override
    public @NotNull Class<?> toJavaClass() {
        return ((ObjectType) getInternalType()).getInnerClass();
    }

    @Override
    public boolean compatibleWith(@NotNull Type type) {
        if (type instanceof ObjectType)
            return toJavaClass().isAssignableFrom(((ObjectType) type).getInnerClass());
        else return false;
    }

    @Override
    public String toString() {
        return ClassType.print(ObjectType.getClassName(((ObjectType) this.object).getInnerClass()));
    }

}
