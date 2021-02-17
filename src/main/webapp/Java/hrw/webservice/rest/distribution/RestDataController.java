package hrw.webservice.rest.distribution;

import com.fasterxml.jackson.core.JsonProcessingException;
import hrw.webservice.logic.Covid19WebService;
import hrw.webservice.logic.WebServiceEndpointHandler;
import hrw.webservice.model.endpoint.ErrorResponseModel;
import hrw.webservice.model.endpoint.RestEndpointModel;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * REST Data Controller with Swagger-UI description
 * Annotation in Spring boot and Swagger documentation
 *
 * @author Lars Karbach, Furkan Kilic
 * @version 1.0
 * @since 2021-02-16
 */

@ApiOperation(value = "/covid")
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Client sent an invalid request"),
        @ApiResponse(code = 500, message = "A generic error occurred on the server")//
})

@RestController
@RequestMapping("/covid")
public class RestDataController {
    private Covid19WebService webService;

    /**Basic constructor
     * @param webService sets web service
     */
    public RestDataController(Covid19WebService webService) {
        this.webService = webService;
    }

    /**
     * @param days A parameter that determines the day
     * @return all Covid-19 data
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 *                                 *                                 serialization/deserialization.
     */
    @GetMapping(value = "data")
    @ApiOperation(value = "Get all Covid-19 data", response = RestEndpointModel.class)
    @ResponseBody
    public String getAllData(@ApiParam(value = "Get all Covid-19 data on choosen day", required = true) @RequestParam int days) throws JsonProcessingException {
        if (days >= 1) {
            return webService.getHandler().generateAllData(days);
        } else {
            return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(400, "Client sent an invalid request"));
        }
    }

    /**
     * @param filter A request parameter that determines latest, target and total
     * @return infections by latest, target or total
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 *                                 *                                 serialization/deserialization.
     */
    @GetMapping(value = "data/infections")
    @ApiOperation(value = "Filter infections by latest, target & total", response = RestEndpointModel.class)
    @ResponseBody
    public String getInfectionsData(@ApiParam(value = "Requires a filter value -> latest, target & total", example = "latest", required = true) @RequestParam String filter) throws JsonProcessingException {
        String responseJson;

        try {
            switch (filter) {
                case "latest":
                    responseJson = webService.getHandler().generateLatestInfections();
                    break;
                case "target":
                    responseJson = webService.getHandler().generateTargetInfections();
                    break;
                case "total":
                    responseJson = webService.getHandler().generateTrueInfections();
                    break;
                default:
                    return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(400, "Client sent an invalid request"));
            }
            return responseJson;
        } catch (Exception e) {
            return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(500, "A generic error occurred on the server"));
        }
    }

    /**
     * @param days A parameter that determines the day
     * @return infections last 24 hours or average raise by "n" days
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 *                                 *                                 serialization/deserialization.
     */
    @GetMapping(value = "data/infections/raised")
    @ApiOperation(value = "Get raised infections last 24 hours and average raise by \"n\" days", notes = "Days over the input >1 will response average raise by \"n\" days " +
            "\n Days with input == 1 will response raised infections last 24 hours", response = RestEndpointModel.class)

    @ResponseBody
    public String getAvgRaisedInfections(int days) throws JsonProcessingException {
        String responseJson;
        try {
            if (days > 1) {
                responseJson = webService.getHandler().generateAvgInfectionRaise(days);
            } else if (days == 1) {
                responseJson = webService.getHandler().generateDailyInfectionsRaise();
            } else {
                return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(400, "Client sent an invalid request"));
            }
        } catch (Exception e) {
            return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(500, "A generic error occurred on the server"));
        }
        return responseJson;
    }

    /**
     * @return incidence in Germany
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 *                                 *                                 serialization/deserialization.
     */
    @GetMapping("data/incidences")
    @ApiOperation(value = "Get incidence", response = RestEndpointModel.class)
    public String getIncidences() throws JsonProcessingException {
        return webService.getHandler().generateIncidences();
    }

    /**
     * @return forecast for remainign Lockdown days
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 *                                 *                                 serialization/deserialization.
     */
    @GetMapping("data/forecast")
    @ApiOperation(value = "Get the lockdown forecast", response = RestEndpointModel.class)
    public String getLockdownForecast() throws JsonProcessingException {
        return webService.getHandler().generateForecast();
    }
}