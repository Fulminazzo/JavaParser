package it.fulminazzo.javaparser.visitors;

import it.fulminazzo.javaparser.parser.node.MethodInvocation;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.parser.node.types.Literal;
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

    /**
     * Converts assignment and its fields to this visitor type.
     *
     * @param type       the type
     * @param name       the name
     * @param value       the value
     * @return the assignment
     */
    T visitAssignment(@NotNull Literal type, @NotNull Literal name, @NotNull Node value);

    /**
     * Converts method call and its fields to this visitor type.
     *
     * @param executor   the executor
     * @param invocation the invocation
     * @return the method call
     */
    T visitMethodCall(@NotNull Literal executor, @NotNull MethodInvocation invocation);

    /**
     * Converts method invocation and its fields to this visitor type.
     *
     * @param parameters the parameters
     * @return the method invocation
     */
    T visitMethodInvocation(@NotNull List<Node> parameters);

    /**
     * Converts dynamic array and its fields to this visitor type.
     *
     * @param parameters the parameters
     * @param type       the type
     * @return the dynamic array
     */
    T visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type);

    /**
     * Converts static array and its fields to this visitor type.
     *
     * @param size the size
     * @param type the type
     * @return the static array
     */
    T visitStaticArray(int size, @NotNull Node type);

    /**
     * Converts code block and its fields to this visitor type.
     *
     * @param statements the statements
     * @return the code block
     */
    T visitCodeBlock(@NotNull LinkedList<Node> statements);

    /**
     * Converts add and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the add
     */
    T visitAdd(@NotNull Node left, @NotNull Node right);

    /**
     * Converts and and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the and
     */
    T visitAnd(@NotNull Node left, @NotNull Node right);

    /**
     * Converts bit and and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the bit and
     */
    T visitBitAnd(@NotNull Node left, @NotNull Node right);

    /**
     * Converts bit or and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the bit or
     */
    T visitBitOr(@NotNull Node left, @NotNull Node right);

    /**
     * Converts bit xor and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the bit xor
     */
    T visitBitXor(@NotNull Node left, @NotNull Node right);

    /**
     * Converts divide and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the divide
     */
    T visitDivide(@NotNull Node left, @NotNull Node right);

    /**
     * Converts equal and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the equal
     */
    T visitEqual(@NotNull Node left, @NotNull Node right);

    /**
     * Converts greater than and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the greater than
     */
    T visitGreaterThan(@NotNull Node left, @NotNull Node right);

    /**
     * Converts greater than equal and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the greater than equal
     */
    T visitGreaterThanEqual(@NotNull Node left, @NotNull Node right);

    /**
     * Converts l shift and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the l shift
     */
    T visitLShift(@NotNull Node left, @NotNull Node right);

    /**
     * Converts less than and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the less than
     */
    T visitLessThan(@NotNull Node left, @NotNull Node right);

    /**
     * Converts less than equal and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the less than equal
     */
    T visitLessThanEqual(@NotNull Node left, @NotNull Node right);

    /**
     * Converts modulo and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the modulo
     */
    T visitModulo(@NotNull Node left, @NotNull Node right);

    /**
     * Converts multiply and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the multiply
     */
    T visitMultiply(@NotNull Node left, @NotNull Node right);

    /**
     * Converts new object and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the new object
     */
    T visitNewObject(@NotNull Node left, @NotNull Node right);

    /**
     * Converts not equal and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the not equal
     */
    T visitNotEqual(@NotNull Node left, @NotNull Node right);

    /**
     * Converts or and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the or
     */
    T visitOr(@NotNull Node left, @NotNull Node right);

    /**
     * Converts r shift and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the r shift
     */
    T visitRShift(@NotNull Node left, @NotNull Node right);

    /**
     * Converts re assign and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the re assign
     */
    T visitReAssign(@NotNull Node left, @NotNull Node right);

    /**
     * Converts subtract and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the subtract
     */
    T visitSubtract(@NotNull Node left, @NotNull Node right);

    /**
     * Converts ur shift and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the ur shift
     */
    T visitURShift(@NotNull Node left, @NotNull Node right);

    /**
     * Converts decrement and its fields to this visitor type.
     *
     * @param before  the before
     * @param operand the operand
     * @return the decrement
     */
    T visitDecrement(boolean before, @NotNull Node operand);

    /**
     * Converts increment and its fields to this visitor type.
     *
     * @param before  the before
     * @param operand the operand
     * @return the increment
     */
    T visitIncrement(boolean before, @NotNull Node operand);

    /**
     * Converts minus and its fields to this visitor type.
     *
     * @param operand the operand
     * @return the minus
     */
    T visitMinus(@NotNull Node operand);

    /**
     * Converts not and its fields to this visitor type.
     *
     * @param operand the operand
     * @return the not
     */
    T visitNot(@NotNull Node operand);

    /**
     * Converts break and its fields to this visitor type.
     *
     * @param expr the expr
     * @return the break
     */
    T visitBreak(@NotNull Node expr);

    /**
     * Converts continue and its fields to this visitor type.
     *
     * @param expr the expr
     * @return the continue
     */
    T visitContinue(@NotNull Node expr);

    /**
     * Converts do statement and its fields to this visitor type.
     *
     * @param code the code
     * @param expr the expr
     * @return the do statement
     */
    T visitDoStatement(@NotNull CodeBlock code, @NotNull Node expr);

    /**
     * Converts enhanced for statement and its fields to this visitor type.
     *
     * @param type     the type
     * @param variable the variable
     * @param code     the code
     * @param expr     the expr
     * @return the enhanced for statement
     */
    T visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable, @NotNull CodeBlock code, @NotNull Node expr);

    /**
     * Converts for statement and its fields to this visitor type.
     *
     * @param assignment the assignment
     * @param increment  the increment
     * @param code       the code
     * @param expr       the expr
     * @return the for statement
     */
    T visitForStatement(@NotNull Node assignment, @NotNull Node increment, @NotNull CodeBlock code, @NotNull Node expr);

    /**
     * Converts if statement and its fields to this visitor type.
     *
     * @param code       the code
     * @param thenBranch the then branch
     * @param expr       the expr
     * @return the if statement
     */
    T visitIfStatement(@NotNull CodeBlock code, Node thenBranch, @NotNull Node expr);

    /**
     * Converts return and its fields to this visitor type.
     *
     * @param expr the expr
     * @return the return
     */
    T visitReturn(@NotNull Node expr);

    /**
     * Converts statement and its fields to this visitor type.
     *
     * @param expr the expr
     * @return the statement
     */
    T visitStatement(@NotNull Node expr);

    /**
     * Converts while statement and its fields to this visitor type.
     *
     * @param code the code
     * @param expr the expr
     * @return the while statement
     */
    T visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expr);

    /**
     * Converts boolean literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the boolean literal
     */
    T visitBooleanLiteral(String rawValue);

    /**
     * Converts char literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the char literal
     */
    T visitCharLiteral(String rawValue);

    /**
     * Converts double literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the double literal
     */
    T visitDoubleLiteral(String rawValue);

    /**
     * Converts empty literal and its fields to this visitor type.
     *
     * @return the empty literal
     */
    T visitEmptyLiteral();

    /**
     * Converts float literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the float literal
     */
    T visitFloatLiteral(String rawValue);

    /**
     * Converts literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the literal
     */
    T visitLiteral(String rawValue);

    /**
     * Converts long literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the long literal
     */
    T visitLongLiteral(String rawValue);

    /**
     * Converts number literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the number literal
     */
    T visitNumberLiteral(String rawValue);

    /**
     * Converts string literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the string literal
     */
    T visitStringLiteral(String rawValue);

}
