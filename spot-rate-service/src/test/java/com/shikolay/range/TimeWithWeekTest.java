package com.shikolay.range;

import org.joda.time.LocalTime;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TimeWithWeekTest {
    private TimeWithWeek left = new TimeWithWeek("mon", new LocalTime("12:00"));
    private TimeWithWeek right = new TimeWithWeek("mon", new LocalTime("13:00"));

    @Test
    public void testLaterTodayPositive() {
        assertTrue(left.laterToday(right));
    }

    @Test
    public void testLaterTodayNegative() {
        assertFalse(right.laterToday(left));
    }

    @Test
    public void testLaterTodayEqual() {
        assertTrue(left.laterToday(left));
    }

    @Test
    public void testEarlierTodayPositive() {
        assertTrue(right.earlierToday(left));
    }

    @Test
    public void testEarlierTodayNegative() {
        assertFalse(left.earlierToday(right));
    }

    @Test
    public void testEarlierTodayEqual() {
        assertTrue(right.earlierToday(right));
    }


}
