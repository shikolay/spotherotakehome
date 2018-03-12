package com.shikolay.range;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class RangePoolTest {
    private RangePool testPool;

    @Before
    public void setUp() {
        testPool = new RangePool();

        TimeWithWeek firstLeft = new TimeWithWeek("mon", new LocalTime("06:00"));
        TimeWithWeek firstRight = new TimeWithWeek("mon", new LocalTime("18:00"));
        PricedTimeRange firstRange = new PricedTimeRange(firstLeft, firstRight, 1500L);
        testPool.addRangeToPool(firstRange);

        TimeWithWeek secondLeft = new TimeWithWeek("tues", new LocalTime("06:00"));
        TimeWithWeek secondRight = new TimeWithWeek("tues", new LocalTime("18:00"));
        PricedTimeRange secondRange = new PricedTimeRange(secondLeft, secondRight, 1500L);
        testPool.addRangeToPool(secondRange);

        TimeWithWeek thirdLeft = new TimeWithWeek("wed", new LocalTime("06:00"));
        TimeWithWeek thirdRight = new TimeWithWeek("wed", new LocalTime("18:00"));
        PricedTimeRange thirdRange = new PricedTimeRange(thirdLeft, thirdRight, 1500L);
        testPool.addRangeToPool(thirdRange);
    }

    @Test
    public void testFindAllEncapsulatingPositive() {

        TimeWithWeek leftBound = new TimeWithWeek("wed", new LocalTime("07:00"));
        TimeWithWeek rightBound = new TimeWithWeek("wed", new LocalTime("12:00"));
        PricedTimeRange testRange = new PricedTimeRange(leftBound, rightBound, 0L);

        List<PricedTimeRange> result = testPool.findAllEncapsulating(testRange);

        assertEquals(result.size(), 1);

        PricedTimeRange encapsulating = result.get(0);

        assertTrue(encapsulating.getPrice() == 1500L);

    }

    @Test
    public void testFindAllEncapsulatingNegative() {

        TimeWithWeek leftBound = new TimeWithWeek("sat", new LocalTime("07:00"));
        TimeWithWeek rightBound = new TimeWithWeek("sat", new LocalTime("20:00"));
        PricedTimeRange testRange = new PricedTimeRange(leftBound, rightBound, 0L);

        List<PricedTimeRange> result = testPool.findAllEncapsulating(testRange);

        assertEquals(result.size(), 0);

    }
}
