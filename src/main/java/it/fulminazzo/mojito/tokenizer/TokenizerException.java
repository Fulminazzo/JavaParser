package it.fulminazzo.mojito.tokenizer;

import org.jetbrains.annotations.NotNull;

/**
 * An exception wrapper for {@link Tokenizer}
 */
public final class TokenizerException extends RuntimeException {

    /**
     * Instantiates a new Tokenizer exception.
     *
     * @param exception the exception that caused the failure
     */
    public TokenizerException(final @NotNull Exception exception) {
        super(exception);
    }

}
