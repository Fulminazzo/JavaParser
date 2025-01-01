package it.fulminazzo.javaparser.environment;

import it.fulminazzo.javaparser.environment.scopetypes.ScopeType;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class MockEnvironment<T> extends Environment<T> {
    private final List<ScopeType> enteredScopes;

    public MockEnvironment() {
        this.enteredScopes = new LinkedList<>();
    }

    @Override
    public Environment<T> enterScope(@NotNull ScopeType scopeType) {
        // Necessary check for super() method call.
        if (this.enteredScopes != null) this.enteredScopes.add(scopeType);
        return super.enterScope(scopeType);
    }

    public boolean enteredScope(final @NotNull ScopeType scopeType) {
        return this.enteredScopes.contains(scopeType);
    }

}
