package it.fulminazzo.javaparser.visitors;

import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.parser.node.Assignment;
import it.fulminazzo.javaparser.parser.node.MethodInvocation;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.parser.node.container.JavaProgram;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import it.fulminazzo.javaparser.parser.node.statements.CaseStatement;
import it.fulminazzo.javaparser.parser.node.statements.CatchStatement;
import it.fulminazzo.javaparser.parser.node.statements.Statement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * A <b>Visitor</b> is a special object capable of reading and parsing
 * each {@link it.fulminazzo.javaparser.parser.node.Node} accordingly.
 * It provides methods for each type of node.
 *
 * @param <T> the returned type
 */
public interface Visitor<T> {

    /**
     * Gets the object executing this visitor.
     *
     * @return the object
     */
    @NotNull Object getExecutingObject();

    /**
     * Gets the environment where all the scoped assignments will be handled.
     *
     * @return the environment
     */
    @NotNull Environment<T> getEnvironment();

    /**
     * Starting point of the {@link Visitor}.
     * It reads the given {@link JavaProgram} using all the methods in this class.
     *
     * @param program the program
     * @return an {@link Optional} containing the parsed value
     */
    @NotNull Optional<T> visitProgram(final @NotNull JavaProgram program);

    /**
     * Converts code block and its fields to this visitor type.
     *
     * @param statements the statements
     * @return the code block
     */
    @NotNull T visitCodeBlock(@NotNull LinkedList<Statement> statements);

    /**
     * Converts java program and its fields to this visitor type.
     *
     * @param statements the statements
     * @return the java program
     */
    @NotNull T visitJavaProgram(@NotNull LinkedList<Statement> statements);

    /**
     * Converts return and its fields to this visitor type.
     *
     * @param expression the expression
     * @return the return
     */
    @NotNull T visitReturn(@NotNull Node expression);

    /**
     * Converts throw and its fields to this visitor type.
     *
     * @param expression the expression
     * @return the throw
     */
    @NotNull T visitThrow(@NotNull Node expression);

    /**
     * Converts break and its fields to this visitor type.
     *
     * @param expression the expression
     * @return the break
     */
    @NotNull T visitBreak(@NotNull Node expression);

    /**
     * Converts continue and its fields to this visitor type.
     *
     * @param expression the expression
     * @return the continue
     */
    @NotNull T visitContinue(@NotNull Node expression);

    /**
     * Converts statement and its fields to this visitor type.
     *
     * @param expression the expression
     * @return the statement
     */
    @NotNull T visitStatement(@NotNull Node expression);

    /**
     * Converts try statement and its fields to this visitor type.
     *
     * @param block        the block
     * @param catchBlocks  the catch blocks
     * @param finallyBlock the finally block
     * @param expression   the expression
     * @return the try statement
     */
    @NotNull T visitTryStatement(@NotNull CodeBlock block, @NotNull List<CatchStatement> catchBlocks,
                                 @NotNull CodeBlock finallyBlock, @NotNull Node expression);

    /**
     * Converts assignment block and its fields to this visitor type.
     *
     * @param assignments the assignments
     * @return the assignment block
     */
    @NotNull T visitAssignmentBlock(@NotNull List<Assignment> assignments);

    /**
     * Converts catch statement and its fields to this visitor type.
     *
     * @param exceptions the exceptions
     * @param block      the block
     * @param expression the expression
     * @return the catch statement
     */
    @NotNull T visitCatchStatement(@NotNull List<Literal> exceptions, @NotNull CodeBlock block, @NotNull Node expression);

    /**
     * Converts switch statement and its fields to this visitor type.
     *
     * @param cases        the cases
     * @param defaultBlock the default block
     * @param expression   the expression
     * @return the switch statement
     */
    @NotNull T visitSwitchStatement(@NotNull List<CaseStatement> cases, @NotNull CodeBlock defaultBlock, @NotNull Node expression);

    /**
     * Converts case statement and its fields to this visitor type.
     *
     * @param block      the block
     * @param expression the expression
     * @return the case statement
     */
    @NotNull T visitCaseStatement(@NotNull CodeBlock block, @NotNull Node expression);

    /**
     * Converts for statement and its fields to this visitor type.
     *
     * @param assignment the assignment
     * @param increment  the increment
     * @param code       the code
     * @param expression the expression
     * @return the for statement
     */
    @NotNull T visitForStatement(@NotNull Node assignment, @NotNull Node increment, @NotNull CodeBlock code, @NotNull Node expression);

    /**
     * Converts enhanced for statement and its fields to this visitor type.
     *
     * @param type       the type
     * @param variable   the variable
     * @param code       the code
     * @param expression the expression
     * @return the enhanced for statement
     */
    @NotNull T visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable, @NotNull CodeBlock code, @NotNull Node expression);

    /**
     * Converts do statement and its fields to this visitor type.
     *
     * @param code       the code
     * @param expression the expression
     * @return the do statement
     */
    @NotNull T visitDoStatement(@NotNull CodeBlock code, @NotNull Node expression);

    /**
     * Converts while statement and its fields to this visitor type.
     *
     * @param code       the code
     * @param expression the expression
     * @return the while statement
     */
    @NotNull T visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expression);

    /**
     * Converts if statement and its fields to this visitor type.
     *
     * @param then       the code executed
     * @param elseBranch the alternative branch
     * @param expression the expression
     * @return the if statement
     */
    @NotNull T visitIfStatement(@NotNull CodeBlock then, @NotNull Node elseBranch, @NotNull Node expression);

    /**
     * Converts assignment and its fields to this visitor type.
     *
     * @param type  the type
     * @param name  the name
     * @param value the value
     * @return the assignment
     */
    @NotNull T visitAssignment(@NotNull Node type, @NotNull Literal name, @NotNull Node value);

    /**
     * Converts re assign and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the re assign
     */
    @NotNull T visitReAssign(@NotNull Node left, @NotNull Node right);

    /**
     * Converts new object and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the new object
     */
    @NotNull T visitNewObject(@NotNull Node left, @NotNull Node right);

    /**
     * Converts dynamic array and its fields to this visitor type.
     *
     * @param parameters the parameters
     * @param type       the type
     * @return the dynamic array
     */
    @NotNull T visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type);

    /**
     * Converts static array and its fields to this visitor type.
     *
     * @param size the size
     * @param type the type
     * @return the static array
     */
    @NotNull T visitStaticArray(int size, @NotNull Node type);

    /**
     * Converts array literal and its fields to this visitor type.
     *
     * @param type the type
     * @return the array literal
     */
    @NotNull T visitArrayLiteral(@NotNull Node type);

    /**
     * Converts increment and its fields to this visitor type.
     *
     * @param before  the before
     * @param operand the operand
     * @return the increment
     */
    @NotNull T visitIncrement(boolean before, @NotNull Node operand);

    /**
     * Converts decrement and its fields to this visitor type.
     *
     * @param before  the before
     * @param operand the operand
     * @return the decrement
     */
    @NotNull T visitDecrement(boolean before, @NotNull Node operand);

    /**
     * Converts method call and its fields to this visitor type.
     *
     * @param executor   the executor
     * @param methodName the method name
     * @param invocation the invocation
     * @return the method call
     */
    @NotNull T visitMethodCall(@NotNull Node executor, @NotNull String methodName, @NotNull MethodInvocation invocation);

    /**
     * Converts field and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the field
     */
    @NotNull T visitField(@NotNull Node left, @NotNull Node right);

    /**
     * Converts method invocation and its fields to this visitor type.
     *
     * @param parameters the parameters
     * @return the method invocation
     */
    @NotNull T visitMethodInvocation(@NotNull List<Node> parameters);

    /**
     * Converts and and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the and
     */
    @NotNull T visitAnd(@NotNull Node left, @NotNull Node right);

    /**
     * Converts or and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the or
     */
    @NotNull T visitOr(@NotNull Node left, @NotNull Node right);

    /**
     * Converts equal and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the equal
     */
    @NotNull T visitEqual(@NotNull Node left, @NotNull Node right);

    /**
     * Converts not equal and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the not equal
     */
    @NotNull T visitNotEqual(@NotNull Node left, @NotNull Node right);

    /**
     * Converts less than and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the less than
     */
    @NotNull T visitLessThan(@NotNull Node left, @NotNull Node right);

    /**
     * Converts less than equal and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the less than equal
     */
    @NotNull T visitLessThanEqual(@NotNull Node left, @NotNull Node right);

    /**
     * Converts greater than and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the greater than
     */
    @NotNull T visitGreaterThan(@NotNull Node left, @NotNull Node right);

    /**
     * Converts greater than equal and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the greater than equal
     */
    @NotNull T visitGreaterThanEqual(@NotNull Node left, @NotNull Node right);

    /**
     * Converts bit and and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the bit and
     */
    @NotNull T visitBitAnd(@NotNull Node left, @NotNull Node right);

    /**
     * Converts bit or and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the bit or
     */
    @NotNull T visitBitOr(@NotNull Node left, @NotNull Node right);

    /**
     * Converts bit xor and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the bit xor
     */
    @NotNull T visitBitXor(@NotNull Node left, @NotNull Node right);

    /**
     * Converts l shift and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the l shift
     */
    @NotNull T visitLShift(@NotNull Node left, @NotNull Node right);

    /**
     * Converts r shift and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the r shift
     */
    @NotNull T visitRShift(@NotNull Node left, @NotNull Node right);

    /**
     * Converts ur shift and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the ur shift
     */
    @NotNull T visitURShift(@NotNull Node left, @NotNull Node right);

    /**
     * Converts add and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the add
     */
    @NotNull T visitAdd(@NotNull Node left, @NotNull Node right);

    /**
     * Converts subtract and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the subtract
     */
    @NotNull T visitSubtract(@NotNull Node left, @NotNull Node right);

    /**
     * Converts multiply and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the multiply
     */
    @NotNull T visitMultiply(@NotNull Node left, @NotNull Node right);

    /**
     * Converts divide and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the divide
     */
    @NotNull T visitDivide(@NotNull Node left, @NotNull Node right);

    /**
     * Converts modulo and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the modulo
     */
    @NotNull T visitModulo(@NotNull Node left, @NotNull Node right);

    /**
     * Converts cast and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the cast
     */
    @NotNull T visitCast(@NotNull Node left, @NotNull Node right);

    /**
     * Converts minus and its fields to this visitor type.
     *
     * @param operand the operand
     * @return the minus
     */
    @NotNull T visitMinus(@NotNull Node operand);

    /**
     * Converts not and its fields to this visitor type.
     *
     * @param operand the operand
     * @return the not
     */
    @NotNull T visitNot(@NotNull Node operand);

    /**
     * Converts null literal and its fields to this visitor type.
     *
     * @return the null literal
     */
    @NotNull T visitNullLiteral();

    /**
     * Converts this literal and its fields to this visitor type.
     *
     * @return the 'this' literal
     */
    @NotNull T visitThisLiteral();

    /**
     * Converts char value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the char value literal
     */
    @NotNull T visitCharValueLiteral(@NotNull String rawValue);

    /**
     * Converts number value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the number value literal
     */
    @NotNull T visitNumberValueLiteral(@NotNull String rawValue);

    /**
     * Converts long value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the long value literal
     */
    @NotNull T visitLongValueLiteral(@NotNull String rawValue);

    /**
     * Converts double value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the double value literal
     */
    @NotNull T visitDoubleValueLiteral(@NotNull String rawValue);

    /**
     * Converts float value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the float value literal
     */
    @NotNull T visitFloatValueLiteral(@NotNull String rawValue);

    /**
     * Converts boolean value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the boolean value literal
     */
    @NotNull T visitBooleanValueLiteral(@NotNull String rawValue);

    /**
     * Converts string value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the string value literal
     */
    @NotNull T visitStringValueLiteral(@NotNull String rawValue);

    /**
     * Converts literal and its fields to this visitor type.
     *
     * @param value the value
     * @return the literal
     */
    @NotNull T visitLiteralImpl(@NotNull String value);

    /**
     * Converts empty literal and its fields to this visitor type.
     *
     * @return the empty literal
     */
    @NotNull T visitEmptyLiteral();

}
