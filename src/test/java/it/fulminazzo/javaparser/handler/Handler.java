package it.fulminazzo.javaparser.handler;

import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.handler.elements.ClassElement;
import it.fulminazzo.javaparser.handler.elements.Element;
import it.fulminazzo.javaparser.handler.elements.ParameterElements;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import it.fulminazzo.javaparser.parser.node.statements.CaseStatement;
import it.fulminazzo.javaparser.parser.node.statements.CatchStatement;
import it.fulminazzo.javaparser.visitors.Visitor;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.LiteralVariableContainer;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class Handler implements Visitor<ClassElement, Element, ParameterElements> {
    private final Object executingObject;
    private final Environment<Element> environment;

    public Handler(Object executingObject) {
        this.executingObject = executingObject;
        this.environment = new Environment<>();
    }

    @Override
    public @NotNull Element visitThrow(@NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Element visitBreak(@NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Element visitContinue(@NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Element visitTryStatement(@NotNull CodeBlock block, @NotNull List<CatchStatement> catchBlocks, @NotNull CodeBlock finallyBlock, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Element visitCatchStatement(@NotNull List<Literal> exceptions, @NotNull CodeBlock block, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Element visitSwitchStatement(@NotNull List<CaseStatement> cases, @NotNull CodeBlock defaultBlock, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Element visitCaseStatement(@NotNull CodeBlock block, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Element visitForStatement(@NotNull Node assignment, @NotNull Node increment, @NotNull CodeBlock code, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Element visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable, @NotNull CodeBlock code, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Element visitDoStatement(@NotNull CodeBlock code, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Element visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Element visitIfStatement(@NotNull CodeBlock then, @NotNull Node elseBranch, @NotNull Node expression) {
        return null;
    }

    @Override
    public @NotNull Element convertVariable(@NotNull ClassElement variableType, @NotNull Element variable) {
        return null;
    }

    @Override
    public @NotNull Element visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type) {
        return null;
    }

    @Override
    public @NotNull Element visitStaticArray(int size, @NotNull Node type) {
        return null;
    }

    @Override
    public @NotNull Element visitArrayIndex(@NotNull Node array, @NotNull Node index) {
        return null;
    }

    @Override
    public @NotNull Element visitArrayLiteral(@NotNull Node type) {
        return null;
    }

    @Override
    public @NotNull ParameterElements visitMethodInvocation(@NotNull List<Node> parameters) {
        return null;
    }

    @Override
    public @NotNull Element visitNullLiteral() {
        return null;
    }

    @Override
    public @NotNull Element visitThisLiteral() {
        return null;
    }

    @Override
    public @NotNull Element visitCharValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull Element visitNumberValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull Element visitLongValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull Element visitDoubleValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull Element visitFloatValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull Element visitBooleanValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull Element visitStringValueLiteral(@NotNull String rawValue) {
        return null;
    }

    @Override
    public @NotNull LiteralVariableContainer<ClassElement, Element, ParameterElements> newLiteralObject(@NotNull String value) {
        return null;
    }

    @Override
    public @NotNull Element visitEmptyLiteral() {
        return null;
    }

    @Override
    public @NotNull RuntimeException exceptionWrapper(@NotNull Exception exception) {
        return null;
    }

    @Override
    public @NotNull RuntimeException cannotResolveSymbol(@NotNull String symbol) {
        return null;
    }

}
