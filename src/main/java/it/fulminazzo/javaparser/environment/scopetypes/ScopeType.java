package it.fulminazzo.javaparser.environment.scopetypes;

/**
 * The type of the scope defines the statement that generated it.
 * This is useful when checking for <code>continue</code> or
 * <code>break</code> statements being executed in the correct context.
 */
public interface ScopeType {
    /**
     * Represents the MAIN scope when starting the program.
     * Should NOT be duplicated.
     */
    MAIN,
    ScopeType CODE_BLOCK = BaseScopeType.CODE_BLOCK;
    ScopeType SWITCH = BaseScopeType.SWITCH;
    ScopeType CASE = BaseScopeType.CASE;
    ScopeType FOR = BaseScopeType.FOR;
    ScopeType WHILE = BaseScopeType.WHILE;
    ScopeType DO = BaseScopeType.DO;

    /**
     * Returns the name of the current scope type.
     *
     * @return the name
     */
    String name();

}
