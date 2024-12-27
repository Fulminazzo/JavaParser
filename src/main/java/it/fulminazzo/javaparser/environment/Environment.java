package it.fulminazzo.javaparser.environment;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Optional;

/**
 * Represents a container for {@link Scope}s.
 *
 * @param <T> the type of the values
 */
public class Environment<T> implements Scoped<T> {
    private final LinkedList<Scope<T>> scopes;

    /**
     * Instantiates a new Environment.
     */
    public Environment() {
        this.scopes = new LinkedList<>();
        enterScope(ScopeType.CODE_BLOCK);
    }

    /**
     * Enters a new {@link Scope} with the given {@link ScopeType} as type.
     *
     * @param scopeType the scope type
     * @return this environment
     */
    public Environment<T> enterScope(final @NotNull ScopeType scopeType) {
        this.scopes.push(new Scope<>(scopeType));
        return this;
    }

    /**
     * Exit the latest {@link Scope}.
     * If one is remaining, throws an {@link IllegalStateException}.
     *
     * @return this environment
     */
    public Environment<T> exitScope() {
        if (isMainScope()) throw new IllegalStateException("Cannot exit from main scope");
        this.scopes.pop();
        return this;
    }

    /**
     * Checks whether the current scope is the main one.
     *
     * @return true if it is
     */
    public boolean isMainScope() {
        return this.scopes.size() < 2;
    }

    @Override
    public @NotNull Optional<T> search(@NotNull final String name) {
        return this.scopes.stream()
                .map(s -> s.search(name))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    @Override
    public void declare(@NotNull String name, @NotNull T value) throws ScopeException {
        if (isDeclared(name)) throw alreadyDeclaredVariable(name);
        else lastScope().declare(name, value);
    }

    @Override
    public void update(@NotNull String name, @NotNull T value) throws ScopeException {
        for (Scope<T> scope : this.scopes)
            if (scope.isDeclared(name)) {
                scope.update(name, value);
                return;
            }
        throw noSuchVariable(name);
    }

    @Override
    public @NotNull ScopeType scopeType() {
        return lastScope().scopeType();
    }

    /**
     * Returns the last declared {@link Scope}.
     *
     * @return the scope
     */
    Scope<T> lastScope() {
        return this.scopes.getLast();
    }

}
