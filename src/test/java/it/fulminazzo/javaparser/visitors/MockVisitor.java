package it.fulminazzo.javaparser.visitors;

import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.visitors.visitorobjects.ClassVisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.ParameterVisitorObjects;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MockVisitor implements Visitor {

    public MockVisitorObject visitMockNode(String name, int version) {
        return new MockVisitorObject(name + version);
    }

    @Override
    public @NotNull Object getExecutingObject() {
        return null;
    }

    @Override
    public @NotNull Environment getEnvironment() {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitThrow(@NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitBreak(@NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitContinue(@NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitCaseStatement(@NotNull CodeBlock block, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitForStatement(@NotNull Node assignment, @NotNull Node increment, @NotNull CodeBlock code, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable, @NotNull CodeBlock code, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitDoStatement(@NotNull CodeBlock code, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitIfStatement(@NotNull CodeBlock then, @NotNull Node elseBranch, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitStaticArray(int size, @NotNull Node type) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitArrayLiteral(@NotNull Node type) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitNullLiteral() {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitThisLiteral() {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitCharValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitNumberValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitLongValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitDoubleValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitFloatValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitBooleanValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitStringValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitLiteralImpl(@NotNull String value) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitEmptyLiteral() {
        return null;
    }

    @Override
    public @NotNull VisitorObject convertVariable(@NotNull ClassVisitorObject variableType, @NotNull VisitorObject variable) {
        return null;
    }

    @Override
    public @NotNull RuntimeException exceptionWrapper(@NotNull Exception exception) {
        return null;
    }

    @Override
    public @NotNull RuntimeException invalidType(@NotNull Class expected, @NotNull Object actual) {
        return null;
    }

    @Override
    public @NotNull ParameterVisitorObjects visitMethodInvocation(@NotNull List parameters) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitDynamicArray(@NotNull List parameters, @NotNull Node type) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitSwitchStatement(@NotNull List cases, @NotNull CodeBlock defaultBlock, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitCatchStatement(@NotNull List exceptions, @NotNull CodeBlock block, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitAssignmentBlock(@NotNull List list) {
        return null;
    }

    @Override
    public @NotNull VisitorObject visitTryStatement(@NotNull CodeBlock block, @NotNull List catchBlocks, @NotNull CodeBlock finallyBlock, @NotNull Node expression) {
        return null;
    }
}
