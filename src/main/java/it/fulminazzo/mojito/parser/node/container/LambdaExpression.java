package it.fulminazzo.mojito.parser.node.container;

import it.fulminazzo.mojito.parser.node.Node;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LambdaExpression extends StatementContainer {
    private final @NotNull List<Node> parameters;

    public LambdaExpression(@NotNull List<Node> parameters, @NotNull CodeBlock codeBlock) {
        super(codeBlock.getStatements());
        this.parameters = parameters;
    }

}
