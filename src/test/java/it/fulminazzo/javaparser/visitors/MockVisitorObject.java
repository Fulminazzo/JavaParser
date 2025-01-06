package it.fulminazzo.javaparser.visitors;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import it.fulminazzo.javaparser.visitors.visitorobjects.ClassVisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.ParameterVisitorObjects;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObjectException;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.FieldContainer;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Getter
public class MockVisitorObject implements VisitorObject {
    private final String string;

    public MockVisitorObject(String string) {
        this.string = string;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public @NotNull FieldContainer getField(@NotNull String fieldName) throws VisitorObjectException {
        return null;
    }

    @Override
    public @NotNull FieldContainer getField(@NotNull Field field) throws VisitorObjectException {
        return null;
    }

    @Override
    public @NotNull VisitorObject invokeMethod(@NotNull String methodName, @NotNull ParameterVisitorObjects parameters) throws VisitorObjectException {
        return null;
    }

    @Override
    public @NotNull VisitorObject invokeMethod(@NotNull Method method, @NotNull ParameterVisitorObjects parameters) throws VisitorObjectException {
        return null;
    }

    @Override
    public @NotNull ClassVisitorObject checkClass() {
        return null;
    }

    @Override
    public @NotNull VisitorObject toPrimitive() {
        return null;
    }

    @Override
    public @NotNull VisitorObject toWrapper() {
        return null;
    }

    @Override
    public @NotNull ClassVisitorObject toClass() {
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

    @Override
    public @NotNull RuntimeException noClassType(@NotNull Class type) {
        return null;
    }

    @Override
    public @NotNull VisitorObject check(@NotNull Class clazz) {
        return null;
    }

}
