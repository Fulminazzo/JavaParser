package it.fulminazzo.javaparser.typechecker.types.objects;

import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link ClassObjectType} with a class different from the default types.
 */
class CustomClassObjectType implements ClassType {
    private final ObjectType internalType;

    /**
     * Instantiates a new Custom class object type.
     *
     * @param internalType the internal type
     */
    public CustomClassObjectType(final @NotNull ObjectType internalType) {
        this.internalType = internalType;
    }

    @Override
    public boolean compatibleWith(@NotNull Type type) {
        if (type instanceof ObjectType) {
            ObjectType objectType = (ObjectType) type;
            return this.internalType.equals(objectType);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode() + this.internalType.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CustomClassObjectType && this.internalType.equals(((CustomClassObjectType) o).internalType);
    }

    @Override
    public String toString() {
        return this.internalType.toString().replace("Type", "ClassType");
    }

}
