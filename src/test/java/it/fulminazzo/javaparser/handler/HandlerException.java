package it.fulminazzo.javaparser.handler;

import org.jetbrains.annotations.NotNull;

public class HandlerException extends RuntimeException {

    public HandlerException(@NotNull String message, Object @NotNull ... args) {
        super(String.format(message, args));
    }

}
