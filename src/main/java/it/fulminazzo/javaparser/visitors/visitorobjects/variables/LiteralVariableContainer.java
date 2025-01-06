package it.fulminazzo.javaparser.visitors.visitorobjects.variables;

import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.environment.NamedEntity;
import it.fulminazzo.javaparser.environment.ScopeException;
import it.fulminazzo.javaparser.visitors.visitorobjects.ClassVisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.LiteralObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.ParameterVisitorObjects;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link VariableContainer} that is also {@link LiteralObject}.
 *
 * @param <C> the type of the {@link ClassVisitorObject}
 * @param <O> the type of the {@link VisitorObject}
 * @param <P> the type of the {@link ParameterVisitorObjects}
 */
public abstract class LiteralVariableContainer<
        C extends ClassVisitorObject<C, O, P>,
        O extends VisitorObject<C, O, P>,
        P extends ParameterVisitorObjects<C, O, P>
        > extends VariableContainer<C, O, P> implements LiteralObject<C, O, P> {
    @Getter
    protected final @NotNull String name;
    protected final @NotNull Environment<O> environment;

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
        super(type, value);
        this.environment = environment;
        this.name = name;
    }

    @Override
    public @NotNull O set(final @NotNull O newValue) {
        try {
            this.environment.update(namedEntity(), newValue);
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
