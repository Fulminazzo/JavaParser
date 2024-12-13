package it.fulminazzo.javaparser.parser;

import org.jetbrains.annotations.NotNull;

/**
 * Exception used to notify about errors coming from {@link JavaParser}.
 */
public class ParserException extends RuntimeException {

    /**
     * Instantiates a new Parser exception.
     *
     * @param message the message of the exception
     */
    public ParserException(final @NotNull String message) {
        super(message);
    }

}
