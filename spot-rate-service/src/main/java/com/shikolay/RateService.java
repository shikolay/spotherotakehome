package com.shikolay;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import com.codahale.metrics.annotation.Timed;
import com.shikolay.dao.JSONRanges;
import com.shikolay.dto.ResultedRate;
import com.shikolay.dto.TimerStats;
import com.shikolay.range.PricedTimeRange;
import com.shikolay.range.TimeWithWeek;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

@Path("rate")
public class RateService {
    private HashMap<Integer, String> dayIndexNameMap;

    public RateService() {
        dayIndexNameMap = new HashMap<>();
        dayIndexNameMap.put(1, "mon");
        dayIndexNameMap.put(2, "tues");
        dayIndexNameMap.put(3, "wed");
        dayIndexNameMap.put(4, "thurs");
        dayIndexNameMap.put(5, "fri");
        dayIndexNameMap.put(6, "sat");
        dayIndexNameMap.put(7, "sun");
    }

    /**
     * Endpoint that allow you to query rate of encapsulating interval,
     * given input interval
     *
     * @param from String representation of interval start time,
     *             for all supported formats look joda time docs
     * @param to   String representation of interval end time,
     *             for all supported formats look joda time docs
     * @return ResultedRate object with rate equal to encapsulating interval,
     * if one exists and 'unavailable' otherwise
     */
    @GET
    @Timed
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ResultedRate getRate(@QueryParam("from") String from,
                                @QueryParam("to") String to) {
        DateTime dateFrom = new DateTime(from);
        DateTime dateTo = new DateTime(to);

        DateTime utcFrom = new DateTime(dateFrom.toInstant(), DateTimeZone.UTC);
        DateTime utcTo = new DateTime(dateTo.toInstant(), DateTimeZone.UTC);

        TimeWithWeek left = new TimeWithWeek(dayIndexNameMap.get(dateFrom.getDayOfWeek()), utcFrom.toLocalTime());
        TimeWithWeek right = new TimeWithWeek(dayIndexNameMap.get(dateTo.getDayOfWeek()), utcTo.toLocalTime());

        PricedTimeRange currentRange = new PricedTimeRange(left, right, 0L);

        List<PricedTimeRange> result = JSONRanges
                .getInstance().getParsedPool()
                .findAllEncapsulating(currentRange);

        ResultedRate rate = new ResultedRate();
        if (result.size() == 0) {
            rate.setRate("unavailable");
        } else {
            PricedTimeRange encapsulating = result.get(0);
            rate.setRate(encapsulating.getPrice().toString());
        }

        return rate;
    }

    /**
     * Metrics stats for /rate endpoint
     *
     * @return TimerStats with mean, median and stdDev
     */
    @Path("/metrics")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TimerStats getMetrics() {
        Timer currentTimer = SharedMetricRegistries.getDefault().timer(MetricRegistry.name(RateService.class, "getRate"));
        Snapshot summary = currentTimer.getSnapshot();

        return new TimerStats(summary.getMean(), summary.getMedian(), summary.getStdDev());
    }
}
