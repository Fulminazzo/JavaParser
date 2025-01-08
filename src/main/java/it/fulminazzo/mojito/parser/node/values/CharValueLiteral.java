package it.fulminazzo.mojito.parser.node.values;

import it.fulminazzo.mojito.parser.node.NodeException;
import it.fulminazzo.mojito.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#CHAR_VALUE} literal.
 */
public class CharValueLiteral extends ValueLiteral {

    /**
     * Instantiates a new Char literal.
     *
     * @param rawValue the raw value
     */
    public CharValueLiteral(final @NotNull String rawValue) throws NodeException {
        super(prepareString(rawValue), TokenType.CHAR_VALUE);
    }

    /**
     * Because of conflicts that may arise from the character <code>'\\'</code>,
     * this function removes the quotes, invokes {@link #unescapeString(String)} on
     * the remaining value, and puts those quotes back before returning the value.
     *
     * @param rawValue the raw value
     * @return the unescaped value
     */
    static @NotNull String prepareString(@NotNull String rawValue) {
        if (rawValue.startsWith("'") && rawValue.endsWith("'")) {
            rawValue = rawValue.substring(1, rawValue.length() - 1);
            rawValue = unescapeString(rawValue);
            return String.format("'%s'", rawValue);
        } else return rawValue;
    }

}
