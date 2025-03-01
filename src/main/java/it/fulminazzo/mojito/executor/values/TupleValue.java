package it.fulminazzo.mojito.executor.values;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.mojito.executor.ExecutorException;
import org.jetbrains.annotations.NotNull;

/**
 * An instance of {@link Tuple} that implements {@link Value}.
 * For internal use only.
 *
 * @param <F> the type of the first object
 * @param <S> the type of the second object
 */
public class TupleValue<F, S> extends Tuple<F, S> implements Value<S> {

    /**
     * Instantiates a new Tuple value.
     *
     * @param first  the first object
     * @param second the second object
     */
    public TupleValue(@NotNull F first, @NotNull S second) {
        super(first, second);
    }

    @Override
    public @NotNull ClassValue<?> toClass() {
        throw ExecutorException.noClassValue(getClass());
    }

}
