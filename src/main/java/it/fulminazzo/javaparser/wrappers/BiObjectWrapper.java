package it.fulminazzo.javaparser.wrappers;

import java.util.Objects;

/**
 * Represents a wrapper for two objects.
 * Provides useful methods like {@link #hashCode()}, {@link #equals(Object)} and {@link #toString()}.
 *
 * @param <F> the type of the first object
 * @param <S> the type of the second object
 */
public class BiObjectWrapper<F, S> {
    protected final F first;
    protected final S second;

    /**
     * Instantiates a new Bi object wrapper.
     *
     * @param first  the first object
     * @param second the second object
     */
    public BiObjectWrapper(F first, S second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode() ^ this.first.hashCode() ^ this.second.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && getClass().equals(o.getClass()) &&
                Objects.equals(this.first, ((BiObjectWrapper<?, ?>) o).first) &&
                Objects.equals(this.second, ((BiObjectWrapper<?, ?>) o).second);
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), this.first, this.second);
    }

}
