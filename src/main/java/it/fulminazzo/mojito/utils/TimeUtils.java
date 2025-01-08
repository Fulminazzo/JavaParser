package it.fulminazzo.mojito.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * A collection of utilities to work with time.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeUtils {

    /**
     * Formats the given time in a
     * <i>&lt;hours&gt; hours, &lt;minutes&gt; minutes, &lt;seconds&gt;.&lt;milliseconds&gt; seconds</i>
     * format.
     * If one value is equal to 0, it will not be displayed.
     *
     * @param timeInMillis the time in millis
     * @return the formatted string
     */
    public static @NotNull String formatTime(long timeInMillis) {
        String output = "";

        long hours = timeInMillis / 3600000;
        timeInMillis %= 3600000;
        if (hours > 0) output += hours + " hours";

        long minutes = timeInMillis / 60000;
        timeInMillis %= 60000;
        if (minutes > 0) {
            if (!output.isEmpty()) output += ", ";
            output += minutes + " minutes";
        }

        double seconds = (double) timeInMillis / 1000;
        if (seconds > 0 || output.isEmpty()) {
            if (!output.isEmpty()) output += ", ";
            long nonDecimal = (long) seconds;
            if (seconds - nonDecimal == 0) output += nonDecimal;
            else output += seconds;
            output += " seconds";
        }

        return output;
    }

}
