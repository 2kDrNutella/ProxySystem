package de.drnutella.proxycore.utils;

import de.drnutella.proxycore.objects.TimeObject;

public class TimeCalculator {

    public static TimeObject fromLongToTimeString(long inputSeconds) {
        long seconds = inputSeconds;
        int minutes = 0;
        int hours = 0;
        int days = 0;

        while (seconds > 59) {
            seconds = seconds - 60;
            minutes++;
        }

        while (minutes > 59) {
            minutes = minutes - 60;
            hours++;
        }

        while (hours > 23) {
            hours = hours - 24;
            days++;
        }

        return new TimeObject(days, hours, minutes, seconds);
    }
}
