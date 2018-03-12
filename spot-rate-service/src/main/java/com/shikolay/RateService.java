package com.shikolay;

import com.shikolay.dao.JSONRanges;
import com.shikolay.dto.ResultedRate;
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

/**
 * Root resource (exposed at "myresource" path)
 */
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
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
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
}
