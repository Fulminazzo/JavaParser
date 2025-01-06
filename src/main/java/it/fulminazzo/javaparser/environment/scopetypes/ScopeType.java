package it.fulminazzo.javaparser.environment.scopetypes;

import it.fulminazzo.fulmicollection.objects.Refl;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

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
    ScopeType MAIN = new MainScopeType();
    ScopeType CODE_BLOCK = BaseScopeType.CODE_BLOCK;
    ScopeType TRY = new TryScopeType();
    ScopeType CATCH = BaseScopeType.CATCH;
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

    /**
     * Gets a new instance of {@link TryScopeType} with the specified exceptions.
     *
     * @param exceptions the exceptions
     * @return the try scope type
     */
    static @NotNull TryScopeType tryScope(final @NotNull Stream<Class<Throwable>> exceptions) {
        return new TryScopeType(exceptions);
    }

    /**
     * Returns all the static {@link ScopeType}s.
     *
     * @return the scope types
     */
    static ScopeType @NotNull [] values() {
        Refl<?> refl = new Refl<>(ScopeType.class);
        return refl.getStaticFields().stream()
                .map(refl::getFieldObject)
                .map(o -> (ScopeType) o)
                .toArray(ScopeType[]::new);
    }

}
