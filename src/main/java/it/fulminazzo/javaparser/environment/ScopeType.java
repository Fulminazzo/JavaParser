package it.fulminazzo.javaparser.environment;

/**
 * The type of the scope defines the statement that generated it.
 * This is useful when checking for <code>continue</code> or
 * <code>break</code> statements being executed in the correct context.
 */
public enum ScopeType {
    CODE_BLOCK,
    SWITCH,
    FOR,
    WHILE,
    DO

}
