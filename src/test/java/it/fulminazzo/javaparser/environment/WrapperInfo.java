package it.fulminazzo.javaparser.environment;

import org.jetbrains.annotations.NotNull;

class WrapperInfo<T> implements Info<T> {
    private final Class<T> internalClass;

    public WrapperInfo(final @NotNull Class<T> internalClass) {
        this.internalClass = internalClass;
    }

    @Override
    public boolean compatibleWith(@NotNull Object object) {
        return object.getClass().isAssignableFrom(this.internalClass);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof WrapperInfo && this.internalClass.equals(((WrapperInfo<?>) o).internalClass);
    }

    @Override
    public int hashCode() {
        return this.internalClass.hashCode();
    }

    @Override
    public String toString() {
        return "WrapperInfo(" + this.internalClass.getSimpleName() + ")";
    }

}
