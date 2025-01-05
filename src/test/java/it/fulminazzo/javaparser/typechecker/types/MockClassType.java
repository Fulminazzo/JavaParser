package it.fulminazzo.javaparser.typechecker.types;

import org.jetbrains.annotations.NotNull;

public class MockClassType extends MockType implements ClassType {

    @Override
    public @NotNull Type toType() {
        return new MockType();
    }

    @Override
    public @NotNull Type cast(@NotNull Type object) {
        return new MockType();
    }

    @Override
    public boolean compatibleWith(@NotNull Type object) {
        return object instanceof MockClassType;
    }

    @Override
    public @NotNull Class<?> toJavaClass() {
        return getClass();
    }

}
