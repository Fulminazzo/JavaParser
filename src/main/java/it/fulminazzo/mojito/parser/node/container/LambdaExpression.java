package it.fulminazzo.mojito.parser.node.container;

import it.fulminazzo.mojito.parser.node.Node;
import it.fulminazzo.mojito.parser.node.statements.Return;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a lambda expression with its parameters.
 */
public class LambdaExpression extends StatementContainer {
    private final @NotNull List<Node> parameters;

    /**
     * Instantiates a new Lambda expression with no parameters
     * and the given expression as returned node.
     *
     * @param expression the expression
     */
    public LambdaExpression(final @NotNull Node expression) {
        this(new LinkedList<>(), expression);
    }

    /**
     * Instantiates a new Lambda expression with one parameter
     * and the given expression as returned node.
     *
     * @param parameter  the parameter
     * @param expression the expression
     */
    public LambdaExpression(final @NotNull Node parameter, final @NotNull Node expression) {
        this(Collections.singletonList(parameter), expression);
    }

    /**
     * Instantiates a new Lambda expression with multiple parameters
     * and the given expression as returned node.
     *
     * @param parameters the parameters
     * @param expression the expression
     */
    public LambdaExpression(final @NotNull List<Node> parameters, final @NotNull Node expression) {
        this(parameters, expression instanceof CodeBlock ? (CodeBlock) expression : new CodeBlock(new Return(expression)));
    }

    /**
     * Instantiates a new Lambda expression with multiple parameters.
     *
     * @param parameters the parameters
     * @param codeBlock  the code block
     */
    public LambdaExpression(final @NotNull List<Node> parameters, final @NotNull CodeBlock codeBlock) {
        super(codeBlock.getStatements());
        this.parameters = parameters;
    }

}
