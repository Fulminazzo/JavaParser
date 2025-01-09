package it.fulminazzo.mojito.typechecker.types;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.mojito.typechecker.TypeCheckerException;
import org.jetbrains.annotations.NotNull;

/**
 * An instance of {@link Tuple} that implements {@link Type}.
 * For internal use only.
 *
 * @param <F> the type of the first object
 * @param <S> the type of the second object
 */
public class TupleType<F, S> extends Tuple<F, S> implements Type {

    /**
     * Instantiates a new Tuple type.
     *
     * @param first  the first object
     * @param second the second object
     */
    public TupleType(@NotNull F first, @NotNull S second) {
        super(first, second);
    }

    @Override
    public @NotNull ClassType toClass() {
        throw TypeCheckerException.noClassType(getClass());
    }

}
