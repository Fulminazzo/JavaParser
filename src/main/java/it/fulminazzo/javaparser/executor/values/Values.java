package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.javaparser.executor.ExecutorException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A collection of static immutable {@link Value}s.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Values {
    public static final @NotNull Value<?> NO_VALUE = new SingletonValue("NONE");
    public static final @NotNull Value<?> NULL_VALUE = new SingletonValue("nullvalue");

    /**
     * Represents a special value of {@link Value} that appears as a singleton.
     */
    static final class SingletonValue implements Value<Object> {
        private final @NotNull String valueName;

        /**
         * Instantiates a new Singleton value.
         *
         * @param valueName the value name
         */
        public SingletonValue(final @NotNull String valueName) {
            this.valueName = valueName;
        }

        @Override
        public @NotNull ClassValue<Object> toClass() {
            throw ExecutorException.noClassValue(getClass());
        }

        @Override
        public @Nullable Object getValue() {
            return null;
        }

        @Override
        public int hashCode() {
            return SingletonValue.class.hashCode() ^ this.valueName.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof SingletonValue && this.valueName.equals(((SingletonValue) o).valueName);
        }

        @Override
        public @NotNull String toString() {
            return this.valueName;
        }

    }

}
