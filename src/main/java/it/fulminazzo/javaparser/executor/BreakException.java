package it.fulminazzo.javaparser.executor;

import it.fulminazzo.javaparser.parser.node.Node;
import lombok.NoArgsConstructor;

/**
 * Represents the exception thrown by {@link Executor#visitBreak(Node)}.
 */
final class BreakException extends RuntimeException {

}
