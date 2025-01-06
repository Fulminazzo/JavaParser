package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import it.fulminazzo.javaparser.visitors.visitorobjects.ClassVisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.ParameterVisitorObjects;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObjectException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

public class MockClassValue implements ClassValue {
    @Override
    public @NotNull Class getValue() {
        return null;
    }

    @Override
    public @NotNull VisitorObject cast(@NotNull VisitorObject object) {
        return null;
    }

    @Override
    public boolean compatibleWith(@NotNull VisitorObject object) {
        return false;
    }

    @Override
    public @NotNull VisitorObject newObject(@NotNull Constructor constructor, @NotNull ParameterVisitorObjects parameters) throws VisitorObjectException {
        return null;
    }

    @Override
    public @NotNull VisitorObject invokeMethod(@NotNull Method method, @NotNull ParameterVisitorObjects parameters) throws VisitorObjectException {
        return null;
    }

    @Override
    public @NotNull VisitorObjectException fieldNotFound(@NotNull ClassVisitorObject classVisitorObject, @NotNull String field) {
        return null;
    }

    @Override
    public @NotNull VisitorObjectException methodNotFound(@NotNull ClassVisitorObject classObject, @NotNull String method, @NotNull ParameterVisitorObjects parameters) {
        return null;
    }

    @Override
    public @NotNull VisitorObjectException typesMismatch(@NotNull ClassVisitorObject classObject, @NotNull Executable method, @NotNull ParameterVisitorObjects parameters) {
        return null;
    }

    @Override
    public @NotNull RuntimeException unsupportedOperation(@NotNull TokenType operator, @NotNull VisitorObject left, @NotNull VisitorObject right) {
        return null;
    }

    @Override
    public @NotNull RuntimeException unsupportedOperation(@NotNull TokenType operator, @NotNull VisitorObject operand) {
        return null;
    }

}
