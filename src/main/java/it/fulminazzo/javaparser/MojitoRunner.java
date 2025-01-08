package it.fulminazzo.javaparser;

import it.fulminazzo.javaparser.environment.NamedEntity;
import it.fulminazzo.javaparser.environment.ScopeException;
import it.fulminazzo.javaparser.executor.ExceptionWrapper;
import it.fulminazzo.javaparser.executor.Executor;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.Values;
import it.fulminazzo.javaparser.parser.JavaParser;
import it.fulminazzo.javaparser.parser.node.container.JavaProgram;
import it.fulminazzo.javaparser.typechecker.TypeChecker;
import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.Types;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

/**
 * An implementation of {@link Runner} for Mojito.
 */
final class MojitoRunner implements Runner {
    private final @NotNull Object executingObject;
    private @Nullable Object latestResult;

    /**
     * Instantiates a new Mojito runner.
     *
     * @param executingObject the executing object
     */
    public MojitoRunner(final @NotNull Object executingObject) {
        this.executingObject = executingObject;
        this.latestResult = Optional.empty();
    }

    @Override
    public @NotNull Optional<?> latestResult() {
        return Optional.ofNullable(this.latestResult);
    }

    @Override
    public @NotNull Optional<?> run(final @NotNull InputStream input, final @NotNull Map<String, Object> variables) {
        final JavaParser parser = new JavaParser();
        final TypeChecker typeChecker = new TypeChecker(this.executingObject);
        final Executor executor = new Executor(this.executingObject);

        for (final String k : variables.keySet())
            try {
                final Object v = variables.get(k);
                final Class<?> vClass = v == null ? null : v.getClass();

                NamedEntity name = NamedEntity.of(k);

                ClassType classType = vClass == null ? (ClassType) Types.NULL_TYPE : ClassType.of(vClass);
                typeChecker.getEnvironment().declare(classType, name, classType.toType());

                Value<?> value = Value.of(v);
                ClassValue<?> classValue = v == null ? (ClassValue<?>) Values.NULL_VALUE : value.toClass();
                executor.getEnvironment().declare(classValue, name, value);
            } catch (ScopeException ignored) {
                // Cannot happen
            }

        parser.setInput(input);
        JavaProgram parsed = parser.parseProgram();
        typeChecker.visitProgram(parsed);

        try {
            this.latestResult = executor.visitProgram(parsed).orElse(null);
            return latestResult();
        } catch (ExceptionWrapper e) {
            throw RunnerException.of(e.getActualException().getValue());
        }
    }

}
