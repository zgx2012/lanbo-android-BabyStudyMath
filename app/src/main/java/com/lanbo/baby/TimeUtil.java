package com.lanbo.baby;

import java.util.Locale;

/**
 * Created by zgx on 17-4-20.
 */

public class TimeUtil {
    public static String formatTime(long time) {
        long hour = time / 3600;
        long min = (time % 3600) / 60;
        long sec = time % 60;

        if (hour >= 24) {
            return "超过1天";
        } else if (hour > 0) {
            return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, min, sec);
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d", min, sec);
        }
    }

    public static String toTimeStr(long time) {
        long hour = time / 3600;
        long min = (time % 3600) / 60;
        long sec = time % 60;

        if (hour >= 24) {
            return "超过1天";
        } else if (hour > 0) {
            return String.format(Locale.getDefault(), "%d小时%d分%d秒", hour, min, sec);
        } else {
            return String.format(Locale.getDefault(), "%d分%d秒", min, sec);
        }
    }

}
