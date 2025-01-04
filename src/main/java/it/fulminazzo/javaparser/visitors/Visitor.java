package it.fulminazzo.javaparser.visitors;

import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.environment.scopetypes.ScopeType;
import it.fulminazzo.javaparser.parser.node.Assignment;
import it.fulminazzo.javaparser.parser.node.MethodInvocation;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.parser.node.container.JavaProgram;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import it.fulminazzo.javaparser.parser.node.statements.CaseStatement;
import it.fulminazzo.javaparser.parser.node.statements.CatchStatement;
import it.fulminazzo.javaparser.parser.node.statements.Statement;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * A <b>Visitor</b> is a special object capable of reading and parsing
 * each {@link it.fulminazzo.javaparser.parser.node.Node} accordingly.
 * It provides methods for each type of node.
 *
 * @param <O> the returned object
 */
public interface Visitor<O extends VisitorObject<?, O, ?>> {

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
    @NotNull Environment<O> getEnvironment();

    /**
     * Starting point of the {@link Visitor}.
     * It reads the given {@link JavaProgram} using all the methods in this class.
     *
     * @param program the program
     * @return an {@link Optional} containing the parsed value
     */
    default @NotNull Optional<O> visitProgram(final @NotNull JavaProgram program) {
        O o = program.accept(this);
        return o.equals(visitEmptyLiteral()) ? Optional.empty() : Optional.of(o);
    }

    /**
     * Converts java program and its fields to this visitor type.
     *
     * @param statements the statements
     * @return the java program
     */
    default @NotNull O visitJavaProgram(final @NotNull LinkedList<Statement> statements) {
        return visitCodeBlock(statements);
    }

    /**
     * Converts code block and its fields to this visitor type.
     *
     * @param statements the statements
     * @return the code block
     */
    default @NotNull O visitCodeBlock(@NotNull LinkedList<Statement> statements) {
        return visitScoped(ScopeType.CODE_BLOCK, () -> {
            O empty = visitEmptyLiteral();
            for (Statement statement : statements) {
                O o = statement.accept(this);
                // Something was returned
                if (!o.equals(empty)) return o;
            }
            return empty;
        });
    }

    /**
     * Converts return and its fields to this visitor type.
     *
     * @param expression the expression
     * @return the return
     */
    default @NotNull O visitReturn(final @NotNull Node expression) {
        return expression.accept(this);
    }

    /**
     * Converts throw and its fields to this visitor type.
     *
     * @param expression the expression
     * @return the throw
     */
    @NotNull O visitThrow(@NotNull Node expression);

    /**
     * Converts break and its fields to this visitor type.
     *
     * @param expression the expression
     * @return the break
     */
    @NotNull O visitBreak(@NotNull Node expression);

    /**
     * Converts continue and its fields to this visitor type.
     *
     * @param expression the expression
     * @return the continue
     */
    @NotNull O visitContinue(@NotNull Node expression);

    /**
     * Converts statement and its fields to this visitor type.
     * Since the only statements allowed are assignments,
     * this method should not return anything.
     *
     * @param expression the expression
     * @return the statement
     */
    default @NotNull O visitStatement(final @NotNull Node expression) {
        expression.accept(this);
        return visitEmptyLiteral();
    }

    /**
     * Converts try statement and its fields to this visitor type.
     *
     * @param block        the block
     * @param catchBlocks  the catch blocks
     * @param finallyBlock the finally block
     * @param expression   the expression
     * @return the try statement
     */
    @NotNull O visitTryStatement(@NotNull CodeBlock block, @NotNull List<CatchStatement> catchBlocks,
                                 @NotNull CodeBlock finallyBlock, @NotNull Node expression);

    /**
     * Converts assignment block and its fields to this visitor type.
     *
     * @param assignments the assignments
     * @return the assignment block
     */
    @NotNull O visitAssignmentBlock(@NotNull List<Assignment> assignments);

    /**
     * Converts catch statement and its fields to this visitor type.
     *
     * @param exceptions the exceptions
     * @param block      the block
     * @param expression the expression
     * @return the catch statement
     */
    @NotNull O visitCatchStatement(@NotNull List<Literal> exceptions, @NotNull CodeBlock block,
                                   @NotNull Node expression);

    /**
     * Converts switch statement and its fields to this visitor type.
     *
     * @param cases        the cases
     * @param defaultBlock the default block
     * @param expression   the expression
     * @return the switch statement
     */
    @NotNull O visitSwitchStatement(@NotNull List<CaseStatement> cases, @NotNull CodeBlock defaultBlock,
                                    @NotNull Node expression);

    /**
     * Converts case statement and its fields to this visitor type.
     *
     * @param block      the block
     * @param expression the expression
     * @return the case statement
     */
    @NotNull O visitCaseStatement(@NotNull CodeBlock block, @NotNull Node expression);

    /**
     * Converts for statement and its fields to this visitor type.
     *
     * @param assignment the assignment
     * @param increment  the increment
     * @param code       the code
     * @param expression the expression
     * @return the for statement
     */
    @NotNull O visitForStatement(@NotNull Node assignment, @NotNull Node increment, @NotNull CodeBlock code,
                                 @NotNull Node expression);

    /**
     * Converts enhanced for statement and its fields to this visitor type.
     *
     * @param type       the type
     * @param variable   the variable
     * @param code       the code
     * @param expression the expression
     * @return the enhanced for statement
     */
    @NotNull O visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable, @NotNull CodeBlock code,
                                         @NotNull Node expression);

    /**
     * Converts do statement and its fields to this visitor type.
     *
     * @param code       the code
     * @param expression the expression
     * @return the do statement
     */
    @NotNull O visitDoStatement(@NotNull CodeBlock code, @NotNull Node expression);

    /**
     * Converts while statement and its fields to this visitor type.
     *
     * @param code       the code
     * @param expression the expression
     * @return the while statement
     */
    @NotNull O visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expression);

    /**
     * Converts if statement and its fields to this visitor type.
     *
     * @param then       the code executed
     * @param elseBranch the alternative branch
     * @param expression the expression
     * @return the if statement
     */
    @NotNull O visitIfStatement(@NotNull CodeBlock then, @NotNull Node elseBranch, @NotNull Node expression);

    /**
     * Converts assignment and its fields to this visitor type.
     *
     * @param type  the type
     * @param name  the name
     * @param value the value
     * @return the assignment
     */
    @NotNull O visitAssignment(@NotNull Node type, @NotNull Literal name, @NotNull Node value);

    /**
     * Converts re assign and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the re assign
     */
    @NotNull O visitReAssign(@NotNull Node left, @NotNull Node right);

    /**
     * Converts new object and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the new object
     */
    @NotNull O visitNewObject(@NotNull Node left, @NotNull Node right);

    /**
     * Converts dynamic array and its fields to this visitor type.
     *
     * @param parameters the parameters
     * @param type       the type
     * @return the dynamic array
     */
    @NotNull O visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type);

    /**
     * Converts static array and its fields to this visitor type.
     *
     * @param size the size
     * @param type the type
     * @return the static array
     */
    @NotNull O visitStaticArray(int size, @NotNull Node type);

    /**
     * Converts array literal and its fields to this visitor type.
     *
     * @param type the type
     * @return the array literal
     */
    @NotNull O visitArrayLiteral(@NotNull Node type);

    /**
     * Converts increment and its fields to this visitor type.
     *
     * @param before  the before
     * @param operand the operand
     * @return the increment
     */
    @NotNull O visitIncrement(boolean before, @NotNull Node operand);

    /**
     * Converts decrement and its fields to this visitor type.
     *
     * @param before  the before
     * @param operand the operand
     * @return the decrement
     */
    @NotNull O visitDecrement(boolean before, @NotNull Node operand);

    /**
     * Converts method call and its fields to this visitor type.
     *
     * @param executor   the executor
     * @param methodName the method name
     * @param invocation the invocation
     * @return the method call
     */
    @NotNull T visitMethodCall(@NotNull Node executor, @NotNull String methodName,
                               @NotNull MethodInvocation invocation);

    /**
     * Converts field and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the field
     */
    @NotNull O visitField(@NotNull Node left, @NotNull Node right);

    /**
     * Converts method invocation and its fields to this visitor type.
     *
     * @param parameters the parameters
     * @return the method invocation
     */
    @NotNull O visitMethodInvocation(@NotNull List<Node> parameters);

    /**
     * Converts and and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the and
     */
    @NotNull
    default O visitAnd(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).and(right.accept(this));
    }

    /**
     * Converts or and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the or
     */
    @NotNull
    default O visitOr(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).or(right.accept(this));
    }

    /**
     * Converts equal and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the equal
     */
    @NotNull
    default O visitEqual(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).equal(right.accept(this));
    }

    /**
     * Converts not equal and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the not equal
     */
    @NotNull
    default O visitNotEqual(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).notEqual(right.accept(this));
    }

    /**
     * Converts less than and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the less than
     */
    @NotNull
    default O visitLessThan(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).lessThan(right.accept(this));
    }

    /**
     * Converts less than equal and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the less than equal
     */
    @NotNull
    default O visitLessThanEqual(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).lessThanEqual(right.accept(this));
    }

    /**
     * Converts greater than and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the greater than
     */
    @NotNull
    default O visitGreaterThan(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).greaterThan(right.accept(this));
    }

    /**
     * Converts greater than equal and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the greater than equal
     */
    @NotNull
    default O visitGreaterThanEqual(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).greaterThanEqual(right.accept(this));
    }

    /**
     * Converts bit and and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the bit and
     */
    @NotNull
    default O visitBitAnd(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).bitAnd(right.accept(this));
    }

    /**
     * Converts bit or and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the bit or
     */
    @NotNull
    default O visitBitOr(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).bitOr(right.accept(this));
    }

    /**
     * Converts bit xor and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the bit xor
     */
    @NotNull
    default O visitBitXor(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).bitXor(right.accept(this));
    }

    /**
     * Converts l shift and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the l shift
     */
    @NotNull
    default O visitLShift(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).lshift(right.accept(this));
    }

    /**
     * Converts r shift and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the r shift
     */
    @NotNull
    default O visitRShift(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).rshift(right.accept(this));
    }

    /**
     * Converts ur shift and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the ur shift
     */
    @NotNull
    default O visitURShift(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).urshift(right.accept(this));
    }

    /**
     * Converts add and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the add
     */
    @NotNull
    default O visitAdd(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).add(right.accept(this));
    }

    /**
     * Converts subtract and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the subtract
     */
    @NotNull
    default O visitSubtract(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).subtract(right.accept(this));
    }

    /**
     * Converts multiply and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the multiply
     */
    @NotNull
    default O visitMultiply(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).multiply(right.accept(this));
    }

    /**
     * Converts divide and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the divide
     */
    @NotNull
    default O visitDivide(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).divide(right.accept(this));
    }

    /**
     * Converts modulo and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the modulo
     */
    @NotNull
    default O visitModulo(@NotNull Node left, @NotNull Node right) {
        return left.accept(this).modulo(right.accept(this));
    }

    /**
     * Converts cast and its fields to this visitor type.
     *
     * @param left  the left
     * @param right the right
     * @return the cast
     */
    @NotNull O visitCast(@NotNull Node left, @NotNull Node right);

    /**
     * Converts minus and its fields to this visitor type.
     *
     * @param operand the operand
     * @return the minus
     */
    @NotNull
    default O visitMinus(@NotNull Node operand) {
        return operand.accept(this).minus();
    }

    /**
     * Converts not and its fields to this visitor type.
     *
     * @param operand the operand
     * @return the not
     */
    @NotNull
    default O visitNot(@NotNull Node operand) {
        return operand.accept(this).not();
    }

    /**
     * Converts null literal and its fields to this visitor type.
     *
     * @return the null literal
     */
    @NotNull O visitNullLiteral();

    /**
     * Converts this literal and its fields to this visitor type.
     *
     * @return the 'this' literal
     */
    @NotNull O visitThisLiteral();

    /**
     * Converts char value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the char value literal
     */
    @NotNull O visitCharValueLiteral(@NotNull String rawValue);

    /**
     * Converts number value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the number value literal
     */
    @NotNull O visitNumberValueLiteral(@NotNull String rawValue);

    /**
     * Converts long value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the long value literal
     */
    @NotNull O visitLongValueLiteral(@NotNull String rawValue);

    /**
     * Converts double value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the double value literal
     */
    @NotNull O visitDoubleValueLiteral(@NotNull String rawValue);

    /**
     * Converts float value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the float value literal
     */
    @NotNull O visitFloatValueLiteral(@NotNull String rawValue);

    /**
     * Converts boolean value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the boolean value literal
     */
    @NotNull O visitBooleanValueLiteral(@NotNull String rawValue);

    /**
     * Converts string value literal and its fields to this visitor type.
     *
     * @param rawValue the raw value
     * @return the string value literal
     */
    @NotNull O visitStringValueLiteral(@NotNull String rawValue);

    /**
     * Converts literal and its fields to this visitor type.
     *
     * @param value the value
     * @return the literal
     */
    @NotNull O visitLiteralImpl(@NotNull String value);

    /**
     * Converts empty literal and its fields to this visitor type.
     *
     * @return the empty literal
     */
    @NotNull O visitEmptyLiteral();

    /**
     * Enters the specified {@link ScopeType}, executes the given function and
     * exits the scope.
     * If an exception is thrown:
     * <ul>
     *     <li>if its {@link RuntimeException}, it is thrown as is;</li>
     *     <li>otherwise, it is wrapped using {@link VisitorException#of(Throwable)}.</li>
     * </ul>
     *
     * @param scope    the scope
     * @param function the function
     * @return the returned type by the function
     */
    default @NotNull O visitScoped(final @NotNull ScopeType scope,
                                   final @NotNull Callable<O> function) {
        try {
            getEnvironment().enterScope(scope);
            O object = function.call();
            getEnvironment().exitScope();
            return object;
        } catch (Exception e) {
            if (e instanceof RuntimeException) throw (RuntimeException) e;
            else throw VisitorException.of(e);
        }
    }

}
