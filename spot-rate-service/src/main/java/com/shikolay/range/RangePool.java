package com.shikolay.range;

import java.util.ArrayList;
import java.util.List;

public class RangePool {
    private List<PricedTimeRange> ranges;

    public RangePool() {
        ranges = new ArrayList<>();
    }

    public void addRangeToPool(PricedTimeRange newRange) {
        ranges.add(newRange);
    }

    public List<PricedTimeRange> findAllEncapsulating(PricedTimeRange queryRange) {
        ArrayList<PricedTimeRange> result = new ArrayList<>();

        for (PricedTimeRange currentRange : ranges) {

        }

        return result;
    }
}
