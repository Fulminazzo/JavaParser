package it.fulminazzo.javaparser.parser;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.fulmicollection.utils.StringUtils;
import it.fulminazzo.javaparser.parser.node.*;
import it.fulminazzo.javaparser.parser.node.arrays.DynamicArray;
import it.fulminazzo.javaparser.parser.node.arrays.StaticArray;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.parser.node.container.JavaProgram;
import it.fulminazzo.javaparser.parser.node.literals.ArrayLiteral;
import it.fulminazzo.javaparser.parser.node.literals.EmptyLiteral;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import it.fulminazzo.javaparser.parser.node.operators.binary.*;
import it.fulminazzo.javaparser.parser.node.operators.unary.Decrement;
import it.fulminazzo.javaparser.parser.node.operators.unary.Increment;
import it.fulminazzo.javaparser.parser.node.operators.unary.Minus;
import it.fulminazzo.javaparser.parser.node.operators.unary.Not;
import it.fulminazzo.javaparser.parser.node.statements.*;
import it.fulminazzo.javaparser.parser.node.values.*;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import it.fulminazzo.javaparser.tokenizer.Tokenizer;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import static it.fulminazzo.javaparser.tokenizer.TokenType.*;

/**
 * A parser to read Java code using {@link Tokenizer} and {@link TokenType}.
 */
@NoArgsConstructor
public class JavaParser extends Parser {

    /**
     * Instantiates a new Java parser.
     *
     * @param input the input
     */
    public JavaParser(@NotNull InputStream input) {
        super(input);
    }

    /**
     * JAVA_PROGRAM := SINGLE_STMT*
     *
     * @return the node
     */
    public @NotNull JavaProgram parseProgram() {
        final LinkedList<Statement> statements = new LinkedList<>();
        nextSpaceless();
        while (lastToken() != EOF) statements.add(parseSingleStatement());
        return new JavaProgram(statements);
    }

    /**
     * BLOCK := CODE_BLOCK | SINGLE_STMT
     *
     * @return the node
     */
    protected @NotNull CodeBlock parseBlock() {
        if (lastToken() == OPEN_BRACE) return parseCodeBlock();
        else return new CodeBlock(parseSingleStatement());
    }

    /**
     * CODE_BLOCK := \{ SINGLE_STMT* \}
     *
     * @return the node
     */
    protected @NotNull CodeBlock parseCodeBlock() {
        consume(OPEN_BRACE);
        final LinkedList<Statement> statements = new LinkedList<>();
        while (lastToken() != CLOSE_BRACE) statements.add(parseSingleStatement());
        consume(CLOSE_BRACE);
        return new CodeBlock(statements);
    }

    /**
     * SINGLE_STMT := STMT | ;
     *
     * @return the node
     */
    protected @NotNull Statement parseSingleStatement() {
        final Statement statement;
        // Read comments
        @NotNull Tokenizer tokenizer = this.getTokenizer();
        if (lastToken() == COMMENT_INLINE) {
            tokenizer.readUntilNextLine();
            return parseSingleStatement();
        } else if (lastToken() == COMMENT_BLOCK_START) {
            getTokenizer().readUntil(COMMENT_BLOCK_END);
            return parseSingleStatement();
        }

        if (lastToken() == SEMICOLON) {
            consume(SEMICOLON);
            statement = new Statement();
        } else statement = parseStatement();
        return statement;
    }

    /**
     * STMT := return EXPR; | break; | continue; |
     *         SWITCH_STMT | FOR_STMT | DO_STMT | WHILE_STMT | IF_STMT
     *         ASSIGNMENT;
     *
     * @return the node
     */
    protected @NotNull Statement parseStatement() {
        final Node exp;
        switch (lastToken()) {
            case RETURN: {
                consume(RETURN);
                Statement returnNode = new Return(parseExpression());
                if (lastToken() == SEMICOLON) consume(SEMICOLON);
                return returnNode;
            }
            case BREAK: {
                consume(BREAK);
                consume(SEMICOLON);
                return new Break();
            }
            case CONTINUE: {
                consume(CONTINUE);
                consume(SEMICOLON);
                return new Continue();
            }
            case SWITCH: return parseSwitchStatement();
            case FOR: return parseForStatement();
            case DO: return parseDoStatement();
            case WHILE: return parseWhileStatement();
            case IF: return parseIfStatement();
            default: {
                exp = parseAssignment();
                consume(SEMICOLON);
            }
        }
        return new Statement(exp);
    }

    /**
     * SWITCH_STMT := switch ...
     *
     * @return the node
     */
    protected @NotNull Statement parseSwitchStatement() {
        //TODO:
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * FOR_STMT := for \( ASSIGNMENT?; EXPR?; EXPR? \) BLOCK | ENHANCED_FOR_STMT
     *
     * @return the node
     */
    protected @NotNull Statement parseForStatement() {
        consume(FOR);
        consume(OPEN_PAR);

        Node assignment;
        if (lastToken() != SEMICOLON) {
            assignment = parseAssignment();
            if (assignment.is(Assignment.class)) {
                Assignment ass = (Assignment) assignment;
                if (!ass.isInitialized() && lastToken() == COLON)
                    return parseEnhancedForStatement(ass);
            }
        } else assignment = new Statement();
        consume(SEMICOLON);

        Node condition = lastToken() == SEMICOLON ? new Statement() : parseExpression();
        consume(SEMICOLON);

        Node increment = lastToken() == CLOSE_PAR ? new Statement() : parseExpression();
        consume(CLOSE_PAR);

        CodeBlock block = parseBlock();
        return new ForStatement(assignment, condition, increment, block);
    }

    /**
     * ENHANCED_FOR_STMT := for \( LITERAL(\[\])? LITERAL : EXPR \) BLOCK
     *
     * @return the node
     */
    private @NotNull EnhancedForStatement parseEnhancedForStatement(final @NotNull Assignment assignment) {
        consume(COLON);
        Node expr = parseExpression();
        consume(CLOSE_PAR);
        CodeBlock block = parseBlock();
        return new EnhancedForStatement(assignment.getType(), assignment.getName(), expr, block);
    }

    /**
     * DO_STMT := do BLOCK while PAR_EXPR
     *
     * @return the node
     */
    protected @NotNull DoStatement parseDoStatement() {
        consume(DO);
        CodeBlock codeBlock = parseBlock();
        consume(WHILE);
        Node expression = parseParenthesizedExpr();
        consume(SEMICOLON);
        return new DoStatement(expression, codeBlock);
    }

    /**
     * WHILE_STMT := while PAR_EXPR BLOCK
     *
     * @return the node
     */
    protected @NotNull WhileStatement parseWhileStatement() {
        consume(WHILE);
        Node expression = parseParenthesizedExpr();
        CodeBlock codeBlock = parseBlock();
        return new WhileStatement(expression, codeBlock);
    }

    /**
     * IF_STMT := if PAR_EXPR BLOCK (else IF_STMT)* (else BLOCK)?
     *
     * @return the node
     */
    protected @NotNull IfStatement parseIfStatement() {
        consume(IF);
        Node expression = parseParenthesizedExpr();
        CodeBlock codeBlock = parseBlock();
        if (lastToken() == ELSE) {
            consume(ELSE);
            if (lastToken() == IF)
                return new IfStatement(expression, codeBlock, parseIfStatement());
            else return new IfStatement(expression, codeBlock, parseBlock());
        }
        return new IfStatement(expression, codeBlock, new Statement());
    }

    /**
     * ASSIGNMENT := LITERAL(\[\])? LITERAL (=EXPR?) | LITERAL = EXPR | EXPR
     *
     * @return the node
     */
    protected @NotNull Node parseAssignment() {
        Node expression = parseExpression();
        if (expression.is(Literal.class)) {
            if (lastToken() == OPEN_BRACKET) {
                consume(OPEN_BRACKET);
                consume(CLOSE_BRACKET);
                expression = new ArrayLiteral(expression);
            }
            if (lastToken() == LITERAL || expression.is(ArrayLiteral.class)) {
                Literal name = parseLiteral();
                Node value = new EmptyLiteral();
                if (lastToken() == ASSIGN) {
                    consume(ASSIGN);
                    value = parseExpression();
                }
                expression = new Assignment(expression, name, value);
            } else {
                consume(ASSIGN);
                expression = new ReAssign(expression, parseExpression());
            }
        }
        return expression;
    }

    /**
     * EXPR := NEW_OBJECT | INCREMENT | DECREMENT | METHOD_CALL
     *
     * @return the node
     */
    protected @NotNull Node parseExpression() {
        Node expression;
        switch (lastToken()) {
            case NEW: {
                expression = parseNewObject();
                break;
            }
            case ADD: {
                expression = parseIncrement();
                break;
            }
            case SUBTRACT: {
                expression = parseDecrement();
                break;
            }
            default: {
                expression = parseMethodCall();
            }
        }
        return expression;
    }

    /**
     * NEW_OBJECT := new LITERAL METHOD_INVOCATION |
     *               new LITERAL\[\]\{ (EXPR)? (, EXPR)* \} |
     *               new LITERAL\[NUMBER_VALUE\]
     *
     * @return the node
     */
    protected @NotNull Node parseNewObject() {
        match(NEW);
        next(); // Necessary space
        consume(SPACE);
        Node literal = parseAtom();
        switch (lastToken()) {
            // array
            case OPEN_BRACKET: {
                consume(OPEN_BRACKET);
                if (lastToken() == NUMBER_VALUE) {
                    NumberValueLiteral size = (NumberValueLiteral) parseTypeValue();
                    consume(CLOSE_BRACKET);
                    return new StaticArray(literal, size);
                } else {
                    consume(CLOSE_BRACKET);
                    List<Node> parameters = new LinkedList<>();
                    consume(OPEN_BRACE);
                    while (lastToken() != CLOSE_BRACE) {
                        parameters.add(parseExpression());
                        if (lastToken() == COMMA) consume(COMMA);
                    }
                    consume(CLOSE_BRACE);
                    return new DynamicArray(literal, parameters);
                }
            }
            default: {
                MethodInvocation invocation = parseMethodInvocation();
                return new NewObject(literal, invocation);
            }
        }
    }

    /**
     * INCREMENT := ++ATOM
     *
     * @return the node
     */
    protected @NotNull Increment parseIncrement() {
        consume(ADD);
        consume(ADD);
        return new Increment(parseAtom(), true);
    }

    /**
     * DECREMENT := --ATOM | MINUS
     *
     * @return the node
     */
    protected @NotNull Node parseDecrement() {
        consume(SUBTRACT);
        if (lastToken() != SUBTRACT) return new Minus(parseExpression());
        consume(SUBTRACT);
        return new Decrement(parseAtom(), true);
    }

    /**
     * METHOD_CALL := EQUAL ( METHOD_INVOCATION )*
     *
     * @return the node
     */
    protected @NotNull Node parseMethodCall() {
        Node node = parseBinaryComparison();
        while (lastToken() == OPEN_PAR)
            node = new MethodCall(node, parseMethodInvocation());
        return node;
    }

    /**
     * METHOD_INVOCATION := \( (EXPR)? (, EXPR)* \)
     *
     * @return the node
     */
    protected @NotNull MethodInvocation parseMethodInvocation() {
        List<Node> parameters = new LinkedList<>();
        consume(OPEN_PAR);
        while (lastToken() != CLOSE_PAR) {
            parameters.add(parseExpression());
            if (lastToken() == COMMA) consume(COMMA);
        }
        consume(CLOSE_PAR);
        return new MethodInvocation(parameters);
    }

    /**
     * EQUAL := NOT_EQUAL (== NOT_EQUAL)*
     *
     * @return the node
     */
    protected @NotNull Node parseBinaryComparison() {
        return parseBinaryComparison(EQUAL);
    }

    /**
     * EQUAL := NOT_EQUAL (== NOT_EQUAL)* <br/>
     * NOT_EQUAL := LESS_THAN (!= LESS_THAN)* <br/>
     * LESS_THAN := LESS_THAN_OR_EQUAL (< LESS_THAN_OR_EQUAL)* <br/>
     * LESS_THAN_OR_EQUAL := GREATER_THAN (<= GREATER_THAN)* <br/>
     * GREATER_THAN := GREATER_THAN_OR_EQUAL (> GREATER_THAN_OR_EQUAL)* <br/>
     * GREATER_THAN_OR_EQUAL := AND (>= AND)* <br/>
     * AND := OR (&& OR)* <br/>
     * OR := BIT_AND (|| BIT_AND)*
     *
     * @param comparison the {@link TokenType} that corresponds to the comparison
     * @return the node
     */
    protected @NotNull Node parseBinaryComparison(final @NotNull TokenType comparison) {
        if (comparison.after(OR)) return parseBinaryOperation();
        else {
            final TokenType nextOperation = TokenType.values()[comparison.ordinal() + 1];
            Node node = parseBinaryComparison(nextOperation);
            while (lastToken() == comparison) {
                consume(comparison);
                Node tmp = parseBinaryComparison(nextOperation);
                node = generateBinaryOperationFromToken(comparison, node, tmp);
            }
            return node;
        }
    }

    /**
     * BIT_AND := BIT_OR (& BIT_OR)*
     *
     * @return the node
     */
    protected @NotNull Node parseBinaryOperation() {
        return parseBinaryOperation(BIT_AND);
    }

    /**
     * BIT_AND := BIT_OR ( (& BIT_OR)* | (&= BIT_OR) )
     * BIT_OR := BIT_XOR ( (| BIT_XOR)* | (|= BIT_XOR) )
     * BIT_XOR := LSHIFT ( (^ LSHIFT)* | (^= LSHIFT) )
     * LSHIFT := RSHIFT ( (<< RSHIFT)* | (<<= RSHIFT) )
     * RSHIFT := URSHIFT ( (>> URSHIFT)* | (>>= URSHIFT) )
     * URSHIFT := ADD ( (>>> ADD)* | (>>>= ADD) )
     * ADD := SUB ( (+ SUB)* | (+= SUB) | ++ )
     * SUB := MUL ( (- MUL)* | (-= MUL) | -- )
     * MUL := DIV ( (* DIV)* | (*= DIV) )
     * DIV := MOD ( (/ MOD)* | (/= MOD) )
     * MOD := ATOM ( (% ATOM)* | (%= ATOM) )
     *
     * @param operation the {@link TokenType} that corresponds to the operation
     * @return the node
     */
    protected @NotNull Node parseBinaryOperation(final @NotNull TokenType operation) {
        if (operation.after(MODULO)) return parseAtom();
        else {
            final TokenType nextOperation = TokenType.values()[operation.ordinal() + 1];
            Node node = parseBinaryOperation(nextOperation);
            while (lastToken() == operation) {
                consume(operation);
                TokenType lastToken = lastToken();
                if (operation == ADD && lastToken == ADD) {
                    consume(ADD);
                    return new Increment(node, false);
                } else if (operation == SUBTRACT && lastToken == SUBTRACT) {
                    consume(SUBTRACT);
                    return new Decrement(node, false);
                } else if (lastToken == ASSIGN) {
                    consume(ASSIGN);
                    Node nextOperationNode = parseBinaryOperation(nextOperation);
                    Node newNode = generateBinaryOperationFromToken(operation, node, nextOperationNode);
                    node = new ReAssign(node, newNode);
                } else {
                    Node nextOperationNode = parseBinaryOperation(nextOperation);
                    node = generateBinaryOperationFromToken(operation, node, nextOperationNode);
                }
            }
            return node;
        }
    }

    /**
     * Creates a new {@link BinaryOperation} object from the given {@link TokenType}.
     * <br>
     * Uses {@link #findBinaryOperationClass(String)} to search the most appropriate class.
     *
     * @param operation the token that identifies the binary operation
     * @param left      the left operand
     * @param right     the right operand
     * @return the node object
     */
    protected @NotNull Node generateBinaryOperationFromToken(final @NotNull TokenType operation,
                                                             final @NotNull Node left,
                                                             final @NotNull Node right) {
        Class<? extends BinaryOperation> clazz = findBinaryOperationClass(operation.name());
        return new Refl<>(clazz, left, right).getObject();
    }

    /**
     * Finds the most appropriate {@link BinaryOperation} class from the given string.
     * <br>
     * Throws {@link IllegalArgumentException} if not found.
     *
     * @param className the class name
     * @return the class
     */
    protected Class<? extends BinaryOperation> findBinaryOperationClass(@NotNull String className) {
        if (className.equals(URSHIFT.name())) return URShift.class;
        else if (className.equals(RSHIFT.name())) return RShift.class;
        else if (className.equals(LSHIFT.name())) return LShift.class;
        else {
            className = StringUtils.capitalize(className).replace("_", "");
            return ReflectionUtils.getClass(BinaryOperation.class.getPackage().getName() + "." + className);
        }
    }

    /**
     * ATOM := CAST MINUS | NOT | LITERAL | TYPE_VALUE
     *
     * @return the node
     */
    protected @NotNull Node parseAtom() {
        switch (lastToken()) {
            case OPEN_PAR: return parseCast();
            case SUBTRACT: return parseMinus();
            case NOT: return parseNot();
            case LITERAL: return parseLiteral();
            default: return parseTypeValue();
        }
    }

    /**
     * CAST := (PAR_EXPR)* (EXPR | PAR_EXPR)
     *
     * @return the node
     */
    protected @NotNull Node parseCast() {
        Node expr = parseParenthesizedExpr();
        if (lastToken().between(NOT, LITERAL) || lastToken() == OPEN_PAR)
            expr = new Cast(expr, parseAtom());
        return expr;
    }

    /**
     * PAR_EXPR := \( EXPR \)
     *
     * @return the node
     */
    protected @NotNull Node parseParenthesizedExpr() {
        consume(OPEN_PAR);
        Node expr = parseExpression();
        consume(CLOSE_PAR);
        return expr;
    }

    /**
     * MINUS := - EXPR
     *
     * @return the node
     */
    protected @NotNull Node parseMinus() {
        consume(SUBTRACT);
        return new Minus(parseExpression());
    }

    /**
     * NOT := ! EXPR
     *
     * @return the node
     */
    protected @NotNull Node parseNot() {
        consume(NOT);
        return new Not(parseExpression());
    }

    /**
     * LITERAL := {@link TokenType#LITERAL}
     *
     * @return the node
     */
    protected @NotNull Literal parseLiteral() {
        final String literal = getTokenizer().lastRead();
        consume(LITERAL);
        try {
            return Literal.of(literal);
        } catch (NodeException e) {
            throw invalidValueProvidedException(literal);
        }
    }

    /**
     * TYPE_VALUE := {@link TokenType#NUMBER_VALUE} | {@link TokenType#LONG_VALUE} |
     *               {@link TokenType#DOUBLE_VALUE} | {@link TokenType#FLOAT_VALUE} |
     *               {@link TokenType#BOOLEAN_VALUE} | {@link TokenType#CHAR_VALUE} |
     *               {@link TokenType#STRING_VALUE}
     *
     * @return the node
     */
    protected @NotNull ValueLiteral parseTypeValue() {
        final String read = getTokenizer().lastRead();
        final ValueLiteral literal;
        switch (lastToken()) {
            case NUMBER_VALUE: {
                literal = createLiteral(NumberValueLiteral.class, read);
                break;
            }
            case LONG_VALUE: {
                literal = createLiteral(LongValueLiteral.class, read);
                break;
            }
            case DOUBLE_VALUE: {
                literal = createLiteral(DoubleValueLiteral.class, read);
                break;
            }
            case FLOAT_VALUE: {
                literal = createLiteral(FloatValueLiteral.class, read);
                break;
            }
            case BOOLEAN_VALUE: {
                literal = createLiteral(BooleanValueLiteral.class, read);
                break;
            }
            case CHAR_VALUE: {
                literal = createLiteral(CharValueLiteral.class, read);
                break;
            }
            case STRING_VALUE: {
                literal = createLiteral(StringValueLiteral.class, read);
                break;
            }
            default:
                throw new ParserException(lastToken(), this);
        }
        nextSpaceless();
        return literal;
    }

    /**
     * Creates a new {@link ValueLiteral} from the given class and rawValue.
     * Throws {@link ParserException} in case a {@link NodeException} occurs.
     *
     * @param literalType the type of the literal
     * @param rawValue    the raw value
     * @return the literal
     * @param <L> the type of the literal
     */
    protected <L extends ValueLiteral> @NotNull L createLiteral(final @NotNull Class<L> literalType,
                                                                final @NotNull String rawValue) {
        try {
            return new Refl<>(literalType, rawValue).getObject();
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof NodeException)
                throw invalidValueProvidedException(rawValue);
            else throw e;
        }
    }

    private @NotNull ParserException invalidValueProvidedException(final @NotNull String value) {
        return new ParserException(String.format("Invalid value '%s' provided for value type %s",
                value, lastToken().name()), this);
    }

}
