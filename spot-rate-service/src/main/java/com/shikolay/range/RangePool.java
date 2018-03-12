package com.shikolay.range;

import com.shikolay.dto.RangeEntry;
import com.shikolay.dto.RangesData;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class RangePool {
    private List<PricedTimeRange> ranges;

    public RangePool() {
        ranges = new ArrayList<>();
    }

    public RangePool(RangesData rawData) {
        ranges = new ArrayList<>();

        for (RangeEntry currentEntry : rawData.getRates()) {
            String[] days = currentEntry.getDays().split(",");
            String[] times = currentEntry.getTimes().split("-");
            Long price = currentEntry.getPrice();

            for (String dayOfTheWeek : days) {
                String startTime = times[0];
                String endTime = times[1];

                TimeWithWeek left = new TimeWithWeek(dayOfTheWeek, parseTimeString(startTime));
                TimeWithWeek right = new TimeWithWeek(dayOfTheWeek, parseTimeString(endTime));

                PricedTimeRange currentRange = new PricedTimeRange(left, right, price);
                addRangeToPool(currentRange);
            }
        }
    }

    private LocalTime parseTimeString(String timeStr) {
        String hours = timeStr.substring(0, 2);
        String mins = timeStr.substring(2);
        return new LocalTime(Integer.parseInt(hours), Integer.parseInt(mins));
    }

    public void addRangeToPool(PricedTimeRange newRange) {
        ranges.add(newRange);
    }

    public List<PricedTimeRange> findAllEncapsulating(PricedTimeRange queryRange) {
        ArrayList<PricedTimeRange> result = new ArrayList<>();

        for (PricedTimeRange currentRange : ranges) {
            if (currentRange.getLeftBound().laterToday(queryRange.getLeftBound()) &&
                    currentRange.getRightBound().earlierToday(queryRange.getRightBound())) {
                result.add(currentRange);
            }
        }

        return result;
    }


}
