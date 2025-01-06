package it.fulminazzo.javaparser.parser.node.values;

import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a {@link TokenType#STRING_VALUE} literal.
 */
public class StringValueLiteral extends ValueLiteral {
    private static final Map<String, String> ESCAPE_CHARACTERS = new HashMap<String, String>() {{
        put("\\t", "\t");
        put("\\r", "\r");
        put("\\n", "\n");
        put("\\f", "\f");
        put("\\b", "\b");
        put("\\'", "'");
        put("\\\"", "\"");
        put("\\\\", "\\");
    }};

    /**
     * Instantiates a new String literal.
     *
     * @param rawValue the raw value
     */
    public StringValueLiteral(final @NotNull String rawValue) throws NodeException {
        super(unescapeString(rawValue), TokenType.STRING_VALUE);
    }

    /**
     * Replaces all the {@link #ESCAPE_CHARACTERS} in the string with their actual values.
     *
     * @param string the string
     * @return the replaced string
     */
    static @NotNull String unescapeString(@NotNull String string) {
        for (String key : ESCAPE_CHARACTERS.keySet())
            string = string.replace(key, ESCAPE_CHARACTERS.get(key));
        return string;
    }

}
