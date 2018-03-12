package com.shikolay;

import com.shikolay.dto.ResultedRate;
import org.joda.time.DateTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Date;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("rate")
public class RateService {

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
        ResultedRate rate = new ResultedRate();
        DateTime dateFrom = new DateTime(from);
        rate.setRate(dateFrom.getMillis());
        return rate;
    }
}
