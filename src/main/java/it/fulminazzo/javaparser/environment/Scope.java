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
    public @NotNull Optional<T> search(@NotNull final String name) {
        return !this.internalMap.containsKey(name) ? Optional.empty() : Optional.of(this.internalMap.get(name));
    }

    @Override
    public void declare(@NotNull String name, @NotNull T value) throws ScopeException {
        if (isDeclared(name)) throw alreadyDeclaredVariable(name);
        else this.internalMap.put(name, value);
    }

    @Override
    public void update(@NotNull String name, @NotNull T value) throws ScopeException {
        if (isDeclared(name)) this.internalMap.put(name, value);
        else throw noSuchVariable(name);
    }

    @Override
    public @NotNull ScopeType scopeType() {
        return this.scopeType;
    }

}
