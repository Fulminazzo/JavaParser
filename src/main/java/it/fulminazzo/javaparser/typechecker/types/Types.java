package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * A collection of static immutable {@link Type}s.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Types {
    public static final @NotNull Type NO_TYPE = new SingletonType("NONE");
    public static final @NotNull Type NULL_TYPE = new SingletonType("nulltype");

    /**
     * Represents a special type of {@link Type} that appears as a singleton.
     */
    static final class SingletonType implements Type {
        private final @NotNull String typeName;

        /**
         * Instantiates a new Singleton type.
         *
         * @param typeName the type name
         */
        public SingletonType(final @NotNull String typeName) {
            this.typeName = typeName;
        }

        @Override
        public @NotNull ClassType toClass() {
            throw TypeCheckerException.noClassType(getClass());
        }

        @Override
        public int hashCode() {
            return SingletonType.class.hashCode() ^ this.typeName.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof SingletonType && this.typeName.equals(((SingletonType) o).typeName);
        }

        @Override
        public String toString() {
            return this.typeName;
        }

    }

}
