package it.fulminazzo.mojito.typechecker.types.variables;

import it.fulminazzo.mojito.typechecker.types.ClassType;
import it.fulminazzo.mojito.typechecker.types.ParameterTypes;
import it.fulminazzo.mojito.typechecker.types.Type;
import it.fulminazzo.mojito.visitors.visitorobjects.variables.VariableContainer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the access to an array element with its index.
 */
public final class ArrayTypeVariableContainer
        extends VariableContainer<ClassType, Type, ParameterTypes, VariableContainer<ClassType, Type, ParameterTypes, ?>>
        implements TypeVariableContainer {

    /**
     * Instantiates a new Array type variable container.
     *
     * @param container the actual array
     * @param type      the class type of the components
     * @param index     the index of the current component
     * @param variable  the type returned by the current component
     */
    public ArrayTypeVariableContainer(@NotNull VariableContainer<ClassType, Type, ParameterTypes, ?> container,
                                      @NotNull ClassType type, @NotNull String index, @NotNull Type variable) {
        super(container, type, index, variable);
    }

    @Override
    public @NotNull Type set(@NotNull Type newValue) {
        return this.type.cast(newValue);
    }

}
