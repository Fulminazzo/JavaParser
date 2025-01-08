package it.fulminazzo.javaparser.utils;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * A collection of utility methods to work with maps.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MapUtils {

    /**
     * Searches the key in the given {@link Map}.
     * If found, returns a {@link Tuple} with the key and value found.
     * Otherwise, throws {@link IllegalArgumentException}.
     *
     * @param <K> the type of the key
     * @param <V> the type of the value
     * @param map the map
     * @param key the key
     * @return the tuple
     */
    public static <K, V> @NotNull Tuple<K, V> getKeyAndValue(final @NotNull Map<K, V> map,
                                                             final @NotNull Object key) {
        for (Map.Entry<K, V> entry : map.entrySet())
            if (entry.getKey().equals(key))
                return new Tuple<>(entry.getKey(), entry.getValue());
        throw new IllegalArgumentException("Could not find key " + key);
    }

}
