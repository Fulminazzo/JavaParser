package it.fulminazzo.javaparser.visitors;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import it.fulminazzo.javaparser.visitors.visitorobjects.ClassVisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.ParameterVisitorObjects;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObjectException;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull Tuple getField(@NotNull String fieldName) throws VisitorObjectException {
        return null;
    }

    @Override
    public @NotNull VisitorObject invokeMethod(@NotNull String methodName, @NotNull ParameterVisitorObjects parameters) throws VisitorObjectException {
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
    public RuntimeException unsupportedOperation(@NotNull TokenType operator, @NotNull VisitorObject left, @NotNull VisitorObject right) {
        return null;
    }

    @Override
    public RuntimeException unsupportedOperation(@NotNull TokenType operator, @NotNull VisitorObject operand) {
        return null;
    }

    @Override
    public RuntimeException noClassType(@NotNull Class type) {
        return null;
    }

    @Override
    public @NotNull VisitorObject check(@NotNull Class clazz) {
        return null;
    }

}
