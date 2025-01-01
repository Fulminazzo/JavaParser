package it.fulminazzo.javaparser.environment.scopetypes;

/**
 * Represents the base {@link ScopeType}s allowed by Java.
 */
enum BaseScopeType implements ScopeType {
    CODE_BLOCK,
    CATCH,
    SWITCH,
    CASE,
    FOR,
    WHILE,
    DO

}
