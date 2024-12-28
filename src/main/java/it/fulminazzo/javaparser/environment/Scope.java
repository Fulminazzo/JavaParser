package it.fulminazzo.javaparser.environment;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A basic scope containing name value pairs of variables.
 *
 * @param <T> the type of the value
 */
class Scope<T> implements Scoped<T> {
    private final Map<ObjectData<T>, T> internalMap;
    private final ScopeType scopeType;

    /**
     * Instantiates a new Scope.
     *
     * @param scopeType the scope type
     */
    public Scope(final @NotNull ScopeType scopeType) {
        this.internalMap = new LinkedHashMap<>();
        this.scopeType = scopeType;
    }

    @Override
    public @NotNull Optional<T> search(@NotNull final String name) {
        return getKey(name).map(this.internalMap::get);
    }

    @Override
    public void declare(@NotNull Info<T> info, @NotNull String name, @NotNull T value) throws ScopeException {
        if (isDeclared(name)) throw alreadyDeclaredVariable(name);
        else this.internalMap.put(new ObjectData<>(info, name), value);
    }

    @Override
    public void update(@NotNull String name, @NotNull T value) throws ScopeException {
        ObjectData<T> key = getKey(name).orElseThrow(() -> noSuchVariable(name));
        if (key.info.compatibleWith(value)) this.internalMap.put(key, value);
        else throw new ScopeException(String.format("Object %s is incompatible with %s", value, key.info));
    }

    @Override
    public @NotNull ScopeType scopeType() {
        return this.scopeType;
    }

    /**
     * Searches the internal map for the associated {@link ObjectData} with the given name.
     *
     * @param name the name
     * @return an optional containing the data (if found)
     */
    public @NotNull Optional<ObjectData<T>> getKey(@NotNull String name) {
        return this.internalMap.keySet().stream().filter(d -> d.name.equals(name)).findFirst();
    }

    /**
     * Represents the information of an object.
     *
     * @param <O> the type of the object
     */
    static class ObjectData<O> {
        private final @NotNull Info<O> info;
        private final @NotNull String name;

        /**
         * Instantiates a new Object data.
         *
         * @param info the info
         * @param name the name
         */
        public ObjectData(final @NotNull Info<O> info, final @NotNull String name) {
            this.info = info;
            this.name = name;
        }

        @Override
        public int hashCode() {
            return this.info.hashCode() + this.name.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof ObjectData) {
                ObjectData<?> other = (ObjectData<?>) o;
                return this.info.equals(other.info) && this.name.equals(other.name);
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format("%s(%s, %s)", getClass().getSimpleName(), this.info, this.name);
        }

    }

}
