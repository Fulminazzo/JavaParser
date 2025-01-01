package it.fulminazzo.javaparser.environment.scopetypes;

/**
 * The type of the scope defines the statement that generated it.
 * This is useful when checking for <code>continue</code> or
 * <code>break</code> statements being executed in the correct context.
 */
public enum ScopeType {
    /**
     * Represents the MAIN scope when starting the program.
     * Should NOT be duplicated.
     */
    MAIN,
    CODE_BLOCK,
    SWITCH,
    CASE,
    FOR,
    WHILE,
    DO

}
