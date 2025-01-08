package it.fulminazzo.mojito.handler;

import org.jetbrains.annotations.NotNull;

public class HandlerException extends RuntimeException {

    public HandlerException(@NotNull String message, Object @NotNull ... args) {
        super(String.format(message, args));
    }

    public HandlerException(@NotNull Exception exception) {
        this(exception.getMessage());
    }

}
