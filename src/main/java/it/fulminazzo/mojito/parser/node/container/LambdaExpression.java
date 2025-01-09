package it.fulminazzo.mojito.parser.node.container;

import it.fulminazzo.mojito.parser.node.Node;
import it.fulminazzo.mojito.parser.node.statements.Return;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LambdaExpression extends StatementContainer {
    private final @NotNull List<Node> parameters;

    public LambdaExpression(final @NotNull Node expression) {
        this(new LinkedList<>(), expression);
    }

    public LambdaExpression(final @NotNull Node parameter, final @NotNull Node expression) {
        this(Collections.singletonList(parameter), expression);
    }

    public LambdaExpression(final @NotNull List<Node> parameters, final @NotNull Node expression) {
        this(parameters, expression instanceof CodeBlock ? (CodeBlock) expression : new CodeBlock(new Return(expression)));
    }

    public LambdaExpression(final @NotNull List<Node> parameters, final @NotNull CodeBlock codeBlock) {
        super(codeBlock.getStatements());
        this.parameters = parameters;
    }

}
