package it.fulminazzo.javaparser.executor;

import it.fulminazzo.javaparser.executor.values.Value;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a wrapper for a {@link Throwable} value.
 */
@Getter
final class ExceptionWrapper extends RuntimeException {
    private final @NotNull Value<? extends Throwable> actualException;

    /**
     * Instantiates a new Exception wrapper.
     *
     * @param actualException the actual exception
     */
    public ExceptionWrapper(final @NotNull Value<? extends Throwable> actualException) {
        this.actualException = actualException;
    }

}
