package it.fulminazzo.javaparser.visitors;

import it.fulminazzo.javaparser.parser.node.MethodInvocation;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import it.fulminazzo.javaparser.parser.node.statements.Statement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * A <b>Visitor</b> is a special object capable of reading and parsing
 * each {@link it.fulminazzo.javaparser.parser.node.Node} accordingly.
 * It provides methods for each type of node.
 *
 * @param <T> the returned type
 */
public interface Visitor<T> {

    T visitAssignment(@NotNull Node type, @NotNull Literal name, @NotNull Node value);

    T visitMethodCall(@NotNull Node executor, @NotNull MethodInvocation invocation);

    T visitMethodInvocation(@NotNull List<Node> parameters);

    T visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type);

    T visitStaticArray(int size, @NotNull Node type);

    T visitCodeBlock(@NotNull LinkedList<Statement> statements);

    T visitJavaProgram(@NotNull LinkedList<Statement> statements);

    T visitArrayLiteral(@NotNull Node type);

    T visitEmptyLiteral();

    T visitLiteralImpl(@NotNull String value);

    T visitAdd(@NotNull Node left, @NotNull Node right);

    T visitAnd(@NotNull Node left, @NotNull Node right);

    T visitBitAnd(@NotNull Node left, @NotNull Node right);

    T visitBitOr(@NotNull Node left, @NotNull Node right);

    T visitBitXor(@NotNull Node left, @NotNull Node right);

    T visitCast(@NotNull Node left, @NotNull Node right);

    T visitDivide(@NotNull Node left, @NotNull Node right);

    T visitEqual(@NotNull Node left, @NotNull Node right);

    T visitGreaterThan(@NotNull Node left, @NotNull Node right);

    T visitGreaterThanEqual(@NotNull Node left, @NotNull Node right);

    T visitLShift(@NotNull Node left, @NotNull Node right);

    T visitLessThan(@NotNull Node left, @NotNull Node right);

    T visitLessThanEqual(@NotNull Node left, @NotNull Node right);

    T visitModulo(@NotNull Node left, @NotNull Node right);

    T visitMultiply(@NotNull Node left, @NotNull Node right);

    T visitNewObject(@NotNull Node left, @NotNull Node right);

    T visitNotEqual(@NotNull Node left, @NotNull Node right);

    T visitOr(@NotNull Node left, @NotNull Node right);

    T visitRShift(@NotNull Node left, @NotNull Node right);

    T visitReAssign(@NotNull Node left, @NotNull Node right);

    T visitSubtract(@NotNull Node left, @NotNull Node right);

    T visitURShift(@NotNull Node left, @NotNull Node right);

    T visitDecrement(boolean before, @NotNull Node operand);

    T visitIncrement(boolean before, @NotNull Node operand);

    T visitMinus(@NotNull Node operand);

    T visitNot(@NotNull Node operand);

    T visitBreak(@NotNull Node expr);

    T visitContinue(@NotNull Node expr);

    T visitDoStatement(@NotNull CodeBlock code, @NotNull Node expr);

    T visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable, @NotNull CodeBlock code, @NotNull Node expr);

    T visitForStatement(@NotNull Node assignment, @NotNull Node increment, @NotNull CodeBlock code, @NotNull Node expr);

    T visitIfStatement(@NotNull CodeBlock code, @NotNull Node thenBranch, @NotNull Node expr);

    T visitReturn(@NotNull Node expr);

    T visitStatement(@NotNull Node expr);

    T visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expr);

    T visitBooleanValueLiteral(@NotNull String rawValue);

    T visitCharValueLiteral(@NotNull String rawValue);

    T visitDoubleValueLiteral(@NotNull String rawValue);

    T visitFloatValueLiteral(@NotNull String rawValue);

    T visitLongValueLiteral(@NotNull String rawValue);

    T visitNumberValueLiteral(@NotNull String rawValue);

    T visitStringValueLiteral(@NotNull String rawValue);

}
