package it.fulminazzo.javaparser.tokenizer;

import org.jetbrains.annotations.NotNull;

/**
 * An exception wrapper for {@link Tokenizer}
 */
public class TokenizerException extends RuntimeException {

    /**
     * Instantiates a new Tokenizer exception.
     *
     * @param exception the exception that caused the failure
     */
    public TokenizerException(final @NotNull Exception exception) {
        super(exception);
    }

}
