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
    private final Map<String, T> internalMap;
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
    public @NotNull Optional<T> find(@NotNull final String name) {
        return !this.internalMap.containsKey(name) ? Optional.empty() : Optional.of(this.internalMap.get(name));
    }

    @Override
    public @NotNull T lookup(@NotNull String name) throws ScopeException {
        if (!this.internalMap.containsKey(name)) noSuchVariable(name);
        return this.internalMap.get(name);
    }

    @Override
    public void define(@NotNull String name, @NotNull T value) throws ScopeException {
        if (this.internalMap.containsKey(name)) alreadyDeclaredVariable(name);
        this.internalMap.put(name, value);
    }

    @Override
    public void update(@NotNull String name, @NotNull T value) throws ScopeException {
        if (!this.internalMap.containsKey(name)) noSuchVariable(name);
        this.internalMap.put(name, value);
    }

    @Override
    public @NotNull ScopeType scopeType() {
        return this.scopeType;
    }

}
