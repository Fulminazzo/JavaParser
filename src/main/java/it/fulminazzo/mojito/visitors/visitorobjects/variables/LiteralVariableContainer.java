package it.fulminazzo.mojito.visitors.visitorobjects.variables;

import it.fulminazzo.mojito.environment.Environment;
import it.fulminazzo.mojito.environment.NamedEntity;
import it.fulminazzo.mojito.environment.ScopeException;
import it.fulminazzo.mojito.visitors.visitorobjects.ClassVisitorObject;
import it.fulminazzo.mojito.visitors.visitorobjects.ParameterVisitorObjects;
import it.fulminazzo.mojito.visitors.visitorobjects.VisitorObject;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link VariableContainer} with a name associated.
 *
 * @param <C> the type of the {@link ClassVisitorObject}
 * @param <O> the type of the {@link VisitorObject}
 * @param <P> the type of the {@link ParameterVisitorObjects}
 */
public abstract class LiteralVariableContainer<
        C extends ClassVisitorObject<C, O, P>,
        O extends VisitorObject<C, O, P>,
        P extends ParameterVisitorObjects<C, O, P>
        > extends VariableContainer<C, O, P, Environment<O>> {

    /**
     * Instantiates a new literal variable container.
     *
     * @param environment the environment
     * @param type        the type
     * @param name        the name
     * @param value       the value
     */
    public LiteralVariableContainer(final @NotNull Environment<O> environment, final @NotNull C type,
                                    final @NotNull String name, final @NotNull O value) {
        super(environment, type, name, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull C getType() {
        try {
            return (C) this.container.lookupInfo(namedEntity());
        } catch (ScopeException e) {
            throw exceptionWrapper(e);
        }
    }

    @Override
    public @NotNull O set(final @NotNull O newValue) {
        try {
            this.container.update(namedEntity(), newValue);
        } catch (ScopeException e) {
            throw exceptionWrapper(e);
        }
        return newValue;
    }

    /**
     * Gets a {@link NamedEntity} from the name.
     *
     * @return named entity
     */
    public @NotNull NamedEntity namedEntity() {
        return NamedEntity.of(this.name);
    }

    /**
     * Wraps the given exception to a user-defined {@link RuntimeException}
     * for it to be thrown later.
     *
     * @param exception the exception
     * @return the runtime exception
     */
    protected abstract @NotNull RuntimeException exceptionWrapper(final @NotNull Exception exception);


}
