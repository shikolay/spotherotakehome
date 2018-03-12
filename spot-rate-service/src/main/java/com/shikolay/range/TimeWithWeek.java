package com.shikolay.range;

import org.joda.time.LocalTime;

public class TimeWithWeek {
    private String weekDay;
    private LocalTime time;

    public TimeWithWeek(String weekDay, LocalTime time) {
        this.weekDay = weekDay;
        this.time = time;
    }

    boolean laterToday(TimeWithWeek input) {
        if (this.weekDay.equals(input.weekDay)) {
            return (this.time.compareTo(input.time) <= 0);
        } else {
            return false;
        }
    }

    boolean earlierToday(TimeWithWeek input) {
        if (this.weekDay.equals(input.weekDay)) {
            return (this.time.compareTo(input.time) >= 0);
        } else {
            return false;
        }
    }

    public String getWeekDay() {
        return weekDay;
    }

    public LocalTime getTime() {
        return time;
    }

}
