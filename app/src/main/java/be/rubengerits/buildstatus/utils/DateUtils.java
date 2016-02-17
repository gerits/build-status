package be.rubengerits.buildstatus.utils;

import android.content.Context;

import be.rubengerits.buildstatus.R;

public class DateUtils extends android.text.format.DateUtils {

    public static final String EMPTY_TIME = "00";

    public static String formatElapsedTime(Context context, long elapsedSeconds) {
        String elapsedTime = formatElapsedTime(elapsedSeconds);
        StringBuilder builder = new StringBuilder();
        String[] split = elapsedTime.split(":");
        int position = split.length - 1;

        addValue(context, split, position, R.plurals.seconds, builder);

        position--;
        addValue(context, split, position, R.plurals.minutes, builder);

        position--;
        addValue(context, split, position, R.plurals.hours, builder);

        return builder.toString();
    }

    private static void addValue(Context context, String[] values, int position, int key, StringBuilder builder) {
        if (position >= 0) {
            String value = values[position];
            if (!EMPTY_TIME.equals(value)) {
                if (!builder.toString().isEmpty()) {
                    builder.insert(0, " ");
                }
                Long number = Long.valueOf(value);
                builder.insert(0, context.getResources().getQuantityString(key, number.intValue(), number));
            }
        }
    }

}
