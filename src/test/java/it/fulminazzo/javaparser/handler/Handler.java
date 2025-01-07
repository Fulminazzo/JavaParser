package it.fulminazzo.javaparser.handler;

import it.fulminazzo.javaparser.environment.MockEnvironment;
import it.fulminazzo.javaparser.handler.elements.ClassElement;
import it.fulminazzo.javaparser.handler.elements.Element;
import it.fulminazzo.javaparser.handler.elements.ParameterElements;
import it.fulminazzo.javaparser.handler.elements.variables.ElementLiteralVariableContainer;
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
import java.util.stream.Collectors;

@Getter
public class Handler implements Visitor<ClassElement, Element, ParameterElements> {
    private final Object executingObject;
    private final MockEnvironment<Element> environment;

    public Handler(Object executingObject) {
        this.executingObject = executingObject;
        this.environment = new MockEnvironment<>();
    }

    public Element visitMockNode(String name, int version) {
        return Element.of(name + version);
    }

    @Override
    public @NotNull Element visitThrow(@NotNull Node expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitBreak(@NotNull Node expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitContinue(@NotNull Node expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitTryStatement(@NotNull CodeBlock block, @NotNull List<CatchStatement> catchBlocks,
                                              @NotNull CodeBlock finallyBlock, @NotNull Node expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitCatchStatement(@NotNull List<Literal> exceptions, @NotNull CodeBlock block,
                                                @NotNull Node expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitSwitchStatement(@NotNull List<CaseStatement> cases, @NotNull CodeBlock defaultBlock,
                                                 @NotNull Node expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitCaseStatement(@NotNull CodeBlock block, @NotNull Node expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitForStatement(@NotNull Node assignment, @NotNull Node increment,
                                              @NotNull CodeBlock code, @NotNull Node expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable,
                                                      @NotNull CodeBlock code, @NotNull Node expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitDoStatement(@NotNull CodeBlock code, @NotNull Node expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitIfStatement(@NotNull CodeBlock then, @NotNull Node elseBranch, @NotNull Node expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element convertVariable(@NotNull ClassElement variableType, @NotNull Element variable) {
        return variableType.cast(variable);
    }

    @Override
    public @NotNull Element visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitStaticArray(int size, @NotNull Node type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitArrayIndex(@NotNull Node array, @NotNull Node index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Element visitArrayLiteral(@NotNull Node type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull ParameterElements visitMethodInvocation(@NotNull List<Node> parameters) {
        return new ParameterElements(parameters.stream().map(n -> n.accept(this)).collect(Collectors.toList()));
    }

    @Override
    public @NotNull Element visitNullLiteral() {
        return Element.of(null);
    }

    @Override
    public @NotNull Element visitThisLiteral() {
        return Element.of(this.executingObject);
    }

    @Override
    public @NotNull Element visitCharValueLiteral(@NotNull String rawValue) {
        return Element.of(rawValue.charAt(0));
    }

    @Override
    public @NotNull Element visitNumberValueLiteral(@NotNull String rawValue) {
        return Element.of(Integer.valueOf(rawValue));
    }

    @Override
    public @NotNull Element visitLongValueLiteral(@NotNull String rawValue) {
        return Element.of(Long.valueOf(rawValue));
    }

    @Override
    public @NotNull Element visitDoubleValueLiteral(@NotNull String rawValue) {
        return Element.of(Double.valueOf(rawValue));
    }

    @Override
    public @NotNull Element visitFloatValueLiteral(@NotNull String rawValue) {
        return Element.of(Float.valueOf(rawValue));
    }

    @Override
    public @NotNull Element visitBooleanValueLiteral(@NotNull String rawValue) {
        return Element.of(Boolean.valueOf(rawValue));
    }

    @Override
    public @NotNull Element visitStringValueLiteral(@NotNull String rawValue) {
        return Element.of(rawValue);
    }

    @Override
    public @NotNull LiteralVariableContainer<ClassElement, Element, ParameterElements> newLiteralObject(@NotNull String value) {
        return new ElementLiteralVariableContainer(this.environment, ClassElement.of(null), value, visitNullLiteral());
    }

    @Override
    public @NotNull Element visitEmptyLiteral() {
        return Element.of(null);
    }

    @Override
    public @NotNull RuntimeException exceptionWrapper(@NotNull Exception exception) {
        return new HandlerException(exception);
    }

    @Override
    public @NotNull RuntimeException cannotResolveSymbol(@NotNull String symbol) {
        return new HandlerException("Cannot resolve symbol '%s'", symbol);
    }

}
