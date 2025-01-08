package it.fulminazzo.javaparser.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeUtils {

    public static @NotNull String formatTime(long timeInMillis) {
        double seconds = (double) timeInMillis / 1000;
        long minutes = (long) (seconds / 60);
        if (minutes > 0) seconds %= 60;
        long hours = minutes / 60;
        if (hours > 0) minutes %= 60;

        String format = "";
        if (hours > 0) format = hours + " hours";
        if (minutes > 0) {
            if (!format.isEmpty()) format += ", ";
            format += minutes + " minutes";
        }
        if (seconds > 0 || format.isEmpty()) {
            if (!format.isEmpty()) format += ", ";
            format += seconds + " seconds";
        }

        return format;
    }

}
