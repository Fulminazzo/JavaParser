package it.fulminazzo.javaparser.parser.node.values;

import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.parser.node.TokenizedNode;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a literal which holds a raw value from the TYPE_VALUE directive.
 */
@Getter
public abstract class ValueLiteral extends TokenizedNode {
    protected final @NotNull String rawValue;

    /**
     * Instantiates a new Value literal.
     *
     * @param rawValue the raw value
     * @param type     the type
     */
    public ValueLiteral(final @NotNull String rawValue,
                        final @NotNull TokenType type) throws NodeException {
        super(rawValue, type);
        Matcher matcher = Pattern.compile(type.regex()).matcher(rawValue);
        matcher.find();
        this.rawValue = matcher.group(matcher.groupCount());
    }

}
