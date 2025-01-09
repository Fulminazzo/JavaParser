package it.fulminazzo.mojito.handler;

import it.fulminazzo.mojito.exceptions.FormatRuntimeException;
import org.jetbrains.annotations.NotNull;

public class HandlerException extends FormatRuntimeException {

    public HandlerException(@NotNull String message, Object @NotNull ... args) {
        super(message, args);
    }

    public HandlerException(@NotNull Exception exception) {
        super(exception);
    }

}
