package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.javaparser.executor.ExecutorException;
import org.jetbrains.annotations.NotNull;

/**
 * An instance of {@link Tuple} that implements {@link Value}.
 * For internal use only.
 *
 * @param <F> the type parameter
 * @param <S> the type parameter
 */
public class TupleValue<F, S> extends Tuple<F, S> implements Value<S> {

    /**
     * Instantiates a new Tuple value.
     *
     * @param first  the first
     * @param second the second
     */
    public TupleValue(@NotNull F first, @NotNull S second) {
        super(first, second);
    }

    @Override
    public @NotNull ClassValue<?> toClass() {
        throw ExecutorException.noClassValue(getClass());
    }

}
