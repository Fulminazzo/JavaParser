package it.fulminazzo.mojito.parser.node.values;

import it.fulminazzo.mojito.parser.node.NodeException;
import it.fulminazzo.mojito.parser.node.TokenizedNode;
import it.fulminazzo.mojito.tokenizer.TokenType;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a literal which holds a raw value from the TYPE_VALUE directive.
 */
@Getter
public abstract class ValueLiteral extends TokenizedNode {
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
    protected final @NotNull String rawValue;

    /**
     * Instantiates a new Value literal.
     *
     * @param rawValue the raw value
     * @param type     the type
     * @throws NodeException in case the value does not match with the {@link TokenType#regex()}
     */
    public ValueLiteral(final @NotNull String rawValue,
                        final @NotNull TokenType type) throws NodeException {
        super(rawValue, type);
        Matcher matcher = Pattern.compile(type.regex()).matcher(rawValue);
        matcher.find();
        this.rawValue = matcher.group(matcher.groupCount());
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
