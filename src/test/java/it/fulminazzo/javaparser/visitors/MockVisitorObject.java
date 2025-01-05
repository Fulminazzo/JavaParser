package it.fulminazzo.javaparser.visitors;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
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
    public @NotNull VisitorObject and(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject or(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject equal(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject notEqual(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject lessThan(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject lessThanEqual(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject greaterThan(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject greaterThanEqual(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject bitAnd(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject bitOr(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject bitXor(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject lshift(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject rshift(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject urshift(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject add(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject subtract(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject multiply(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject divide(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject modulo(@NotNull VisitorObject other) {
        return null;
    }

    @Override
    public @NotNull VisitorObject minus() {
        return null;
    }

    @Override
    public @NotNull VisitorObject not() {
        return null;
    }

    @Override
    public @NotNull VisitorObject check(@NotNull Class clazz) {
        return null;
    }
}
