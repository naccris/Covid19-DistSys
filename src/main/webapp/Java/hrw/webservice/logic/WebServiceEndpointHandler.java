package hrw.webservice.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import hrw.webservice.model.endpoint.ErrorResponseModel;
import hrw.webservice.model.endpoint.RestEndpointModel;

import java.util.HashMap;

/**
 * The Handler class serves as a middleman between the endpoints and the computation of the data. Thus,
 * the requests to the endpoints invoke the methods of the handler. Within the Handler then the respective computations
 * take place around so the demanded data to receive.
 *
 * @author Lars Karbach, Furkan Kilic, Philip Klein
 * @version 1.0
 * @since 2021-02-16
 */
public class WebServiceEndpointHandler {
    /**
     * The handler needs a reference of the web service within the class to retrieve
     * the functionality of the web service.
     */
    private final Covid19WebService webService;
    /**
     * The infection model is used as a serialization object to create a JSON string using the object mapper.
     */
    private RestEndpointModel infectionsModel;
    /**
     * The HashMap is holds individual integer data of the infection model in order not to return the whole object
     * with empty properties in case of single requests.
     */
    private HashMap<String, Integer> responseIntMap;
    /**
     * The HashMap is holds individual double data of the infection model in order not to return the whole object
     * with empty properties in case of single requests.
     */
    private HashMap<String, Double> responseDblMap;

    /**
     * Constructor of the EndpointHandler
     *
     * @param webService Requires a reference to the WebService object
     *                   in order to access the objects held in the WebService.
     */
    public WebServiceEndpointHandler(Covid19WebService webService) {
        this.webService = webService;
    }

    /**
     * The method calculates all values that can be retrieved from the WebService and then creates an infection model.
     *
     * @param days The Days parameter describes the number of days for which the calculations should be performed.
     * @return Returns a JSON string containing all the properties of the infection model. (String)
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 serialization/deserialization.
     */
    public String generateAllData(int days) throws JsonProcessingException {
        double avgDecrease;
        double targetInfections;
        double lockdownForecast;
        int trueInfections;

        try {
            infectionsModel = new RestEndpointModel();

            infectionsModel.setIncidence(webService.getRkiTotalData().get(webService.getRkiTotalData().size() - 1).getIncidenceGermany());
            infectionsModel.setTargetInfection(webService.getCalcRKIKeyFigures().calcTargetInfection(webService.getCalcJHKeyFigures().getTrueInfected(), webService.getRkiTotalData().get(webService.getRkiTotalData().size() - 1).getIncidenceGermany()));
            infectionsModel.setNewInfections24H(webService.getCalcJHKeyFigures().getNewInf24H());
            infectionsModel.setTotalInfections(webService.getCalcJHKeyFigures().getTrueInfected());
            infectionsModel.setRaisedInfections(webService.getCalcJHKeyFigures().getRaisedInf24H());
            infectionsModel.setAverageRaise(webService.getCalcJHKeyFigures().calcAvgRaiseN(webService.getJHDailyInfosList(), days));

            avgDecrease = webService.getCalcJHKeyFigures().calcAvgDecrease(webService.getJHDailyInfosList(), 7);
            targetInfections = webService.getCalcRKIKeyFigures().calcTargetInfection(webService.getCalcJHKeyFigures().getTrueInfected(), webService.getRkiTotalData().get(webService.getRkiTotalData().size() - 1).getIncidenceGermany());
            trueInfections = webService.getCalcJHKeyFigures().getTrueInfected();
            lockdownForecast = webService.getCalcRKIKeyFigures().calcRemLockdown(avgDecrease, targetInfections, trueInfections);

            infectionsModel.setRemainingLockdown(lockdownForecast);

            return webService.getJacksonObjMapper().objectToString(infectionsModel);
        } catch (Exception ex) {
            return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(500, "A generic error occurred on the server, in Method generateAllData!"));
        }
    }

    /**
     * The method calls the calculation of the new infections within 24 hours and
     * then creates a JSON string using the Jackson Object mapper.
     *
     * @return Returns a JSON string containing the new infections within 24 hours. (String)
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 serialization/deserialization.
     */
    public String generateLatestInfections() throws JsonProcessingException {
        try {
            responseIntMap = new HashMap<>();
            responseIntMap.put("newInfections24H", webService.getCalcJHKeyFigures().getNewInf24H());
            return webService.getJacksonObjMapper().getJsonFromIntMap(responseIntMap);
        } catch (Exception ex) {
            return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(500, "A generic error occurred on the server, in Method generateLatestInfections!"));
        }
    }

    /**
     * The method calls the calculation of the target total infections and then creates
     * a JSON string from the calculated values.
     *
     * @return Returns a JSON string containing the target infections value. (String)
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 serialization/deserialization.
     */
    public String generateTargetInfections() throws JsonProcessingException {
        try {
            responseDblMap = new HashMap<>();
            responseDblMap.put("targetInfection", webService.getCalcRKIKeyFigures().calcTargetInfection(
                    webService.getCalcJHKeyFigures().getTrueInfected(), webService.getRkiTotalData().get(webService.getRkiTotalData().size() - 1).getIncidenceGermany()));
            return webService.getJacksonObjMapper().getJsonFromDoubleMap(responseDblMap);
        } catch (Exception ex) {
            return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(500, "A generic error occurred on the server, in Method generateTargetInfections!"));
        }
    }

    /**
     * The method calls the calculation of the genuine number of infections from
     * the web service and then creates a JSON string using the Jackson Object mapper.
     *
     * @return Returns a JSON string containing the genuine number of infections. (String)
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 serialization/deserialization.
     */
    public String generateTrueInfections() throws JsonProcessingException {
        try {
            responseIntMap = new HashMap<>();
            responseIntMap.put("totalInfections", webService.getCalcJHKeyFigures().getTrueInfected());
            return webService.getJacksonObjMapper().getJsonFromIntMap(responseIntMap);
        } catch (Exception ex) {
            return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(500, "A generic error occurred on the server, in Method generateTrueInfections!"));
        }
    }

    /**
     * The method calls the calculation of the average increase in infections in n days from
     * the web service and then creates a JSON string using the Jackson Object mapper.
     *
     * @param days The Days parameter defines the number of days for which the calculation should be performed.
     * @return Returns a JSON string containing the average increase in infections in n days. (String)
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 serialization/deserialization.
     */
    public String generateAvgInfectionRaise(int days) throws JsonProcessingException {
        try {
            responseDblMap = new HashMap<>();
            responseDblMap.put("averageRaise", webService.getCalcJHKeyFigures().calcAvgRaiseN(webService.getJHDailyInfosList(), days));

            return webService.getJacksonObjMapper().getJsonFromDoubleMap(responseDblMap);
        } catch (Exception ex) {
            return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(500, "A generic error occurred on the server, in Method generateAvgRaise!"));
        }

    }

    /**
     * The method calls the calculation of the daily increase in infections from
     * the web service and then creates a JSON string using the Jackson Object mapper.
     *
     * @return Returns a JSON string containing the daily increase in infections. (String)
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 serialization/deserialization.
     */
    public String generateDailyInfectionsRaise() throws JsonProcessingException {
        responseIntMap = new HashMap<>();
        responseIntMap.put("raisedInfections", webService.getCalcJHKeyFigures().getRaisedInf24H());
        return webService.getJacksonObjMapper().getJsonFromIntMap(responseIntMap);
    }

    /**
     * Calls the calculation of the daily incidence value and creates a JSON string using the Jackson Object mapper.
     *
     * @return Returns a JSON string containing the incidence value. (String)
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 serialization/deserialization.
     */
    public String generateIncidences() throws JsonProcessingException {
        try {
            responseDblMap = new HashMap<>();
            responseDblMap.put("incidence", webService.getRkiTotalData().get(webService.getRkiTotalData().size() - 1).getIncidenceGermany());

            return webService.getJacksonObjMapper().getJsonFromDoubleMap(responseDblMap);
        } catch (Exception e) {
            return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(500, "A generic error occurred on the server, in Method generateIncidences!"));
        }
    }

    /**
     * Calls the calculation of the  remaining lockdown days and creates a JSON string using the Jackson Object mapper.
     *
     * @return Returns JSON string containing the remaining lockdown days. (String)
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 serialization/deserialization.
     */
    public String generateForecast() throws JsonProcessingException {
        double avgDecrease;
        double lockdownForecast;
        double targetInfections;
        int trueInfections;

        try {
            responseDblMap = new HashMap<>();
            avgDecrease = webService.getCalcJHKeyFigures().calcAvgDecrease(webService.getJHDailyInfosList(), 7);
            targetInfections = webService.getCalcRKIKeyFigures().calcTargetInfection(
                    webService.getCalcJHKeyFigures().getTrueInfected(), webService.getRkiTotalData().get(webService.getRkiTotalData().size() - 1).getIncidenceGermany());
            trueInfections = webService.getCalcJHKeyFigures().getTrueInfected();
            lockdownForecast = webService.getCalcRKIKeyFigures().calcRemLockdown(avgDecrease, targetInfections, trueInfections);

            responseDblMap.put("remainingLockdown", lockdownForecast);
            return webService.getJacksonObjMapper().getJsonFromDoubleMap(responseDblMap);
        } catch (Exception exception) {
            return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(500, "A generic error occurred on the server, in Method generateForecast!"));
        }
    }
}
