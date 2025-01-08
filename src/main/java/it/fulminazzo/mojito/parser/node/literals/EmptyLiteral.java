package it.fulminazzo.mojito.parser.node.literals;

import it.fulminazzo.mojito.parser.node.NodeImpl;
import lombok.NoArgsConstructor;

/**
 * Represents the absence of an expression.
 * Used in many cases (methods invocations, uninitialized assignments, endless for loops and more).
 */
@NoArgsConstructor
public class EmptyLiteral extends NodeImpl {
}
