package it.fulminazzo.javaparser.visitors.visitorobjects;

import it.fulminazzo.javaparser.environment.NamedEntity;

/**
 * Represents a special {@link VisitorObject} that extends {@link NamedEntity}.
 *
 * @param <C> the type of the {@link ClassVisitorObject}
 * @param <O> the type of the {@link VisitorObject}
 * @param <P> the type of the {@link ParameterVisitorObjects}
 */
public interface LiteralObject<
        C extends ClassVisitorObject<C, O, P>,
        O extends VisitorObject<C, O, P>,
        P extends ParameterVisitorObjects<C, O, P>
        > extends VisitorObject<C, O, P>, NamedEntity {
}
