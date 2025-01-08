package it.fulminazzo.mojito.environment;

import it.fulminazzo.mojito.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

class WrapperInfo<T> extends ObjectWrapper<Class<T>> implements Info {

    public WrapperInfo(final @NotNull Class<T> internalClass) {
        super(internalClass);
    }

    @Override
    public boolean compatibleWith(@NotNull Object object) {
        return object.getClass().isAssignableFrom(this.object);
    }

    @Override
    public String toString() {
        return "WrapperInfo(" + this.object.getSimpleName() + ")";
    }

}
