package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import org.jetbrains.annotations.NotNull;

/**
 * An instance of {@link Tuple} that implements {@link Type}.
 * For internal use only.
 *
 * @param <F> the type parameter
 * @param <S> the type parameter
 */
public class TupleType<F, S> extends Tuple<F, S> implements Type {

    /**
     * Instantiates a new Tuple type.
     *
     * @param first  the first
     * @param second the second
     */
    public TupleType(@NotNull F first, @NotNull S second) {
        super(first, second);
    }

    @Override
    public @NotNull ClassType toClassType() {
        throw TypeCheckerException.noClassType(getClass());
    }

}
