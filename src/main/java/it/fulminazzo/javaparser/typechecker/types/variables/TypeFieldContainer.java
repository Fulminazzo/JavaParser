package it.fulminazzo.javaparser.typechecker.types.variables;

import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.ParameterTypes;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.FieldContainer;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of {@link FieldContainer} for {@link Type}.
 */
public final class TypeFieldContainer extends FieldContainer<ClassType, Type, ParameterTypes> implements Type {

    /**
     * Instantiates a new Field container.
     *
     * @param type  the type
     * @param value the value
     */
    public TypeFieldContainer(@NotNull ClassType type, @NotNull Type value) {
        super(type, value);
    }

    @Override
    public @NotNull Type set(@NotNull Type newValue) {
        return newValue;
    }

}
