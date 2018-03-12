package com.shikolay.range;


public class PricedTimeRange {
    private TimeWithWeek leftBound;
    private TimeWithWeek rightBound;
    private Long price;

    public PricedTimeRange(TimeWithWeek leftBound,
                           TimeWithWeek rightBound,
                           Long price) {
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.price = price;
    }

    public TimeWithWeek getLeftBound() {
        return leftBound;
    }

    public TimeWithWeek getRightBound() {
        return rightBound;
    }

    public Long getPrice() {
        return price;
    }
}
