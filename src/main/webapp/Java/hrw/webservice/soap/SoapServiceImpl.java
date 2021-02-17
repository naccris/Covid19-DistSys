package hrw.webservice.soap;

import com.fasterxml.jackson.core.JsonProcessingException;
import hrw.webservice.logic.Covid19WebService;
import hrw.webservice.logic.WebServiceEndpointHandler;
import hrw.webservice.model.endpoint.ErrorResponseModel;

import javax.jws.WebService;

/**
 * The SoapServiceImpl class implements the SoapServiceInterface Interface.
 * All methods annotated with @Override are legal method calls within the client.
 *
 * @author Matthias Klaeßen
 * @version 1.0
 * @since 2021-02-16
 */
@WebService(endpointInterface = "hrw.webservice.soap.SoapServiceInterface")
public class SoapServiceImpl implements SoapServiceInterface {

    /**
     * The instance of the webservice required to get data from backend.
     */
    private static Covid19WebService webService = Covid19WebService.getInstance();

    /**
     * Standard constructor needed by sun-jaxws.xml to initialize the SOAP Web Service.
     */
    public SoapServiceImpl() {

    }

    /**
     * Basic constructor of the SOAP service
     *
     * @param webService current web service
     */
    public SoapServiceImpl(Covid19WebService webService) {
        this.webService = webService;
    }


    /**
     * Returns combined results of calculations based on data provided by John Hopkins University and Robert-Koch-Institute.
     * JsonProcessingException is to be handled on client side.
     *
     * @param days number required by average increase
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Override
    public String getTotalDataJHRKI(int days) throws JsonProcessingException {
        return webService.getHandler().generateAllData(days);
    }

    /**
     * Returns the number of new corona virus infections of the last 24 hours.
     * JsonProcessingException is to be handled on client side.
     *
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Override
    public String getNewInfectionsLast24h() throws JsonProcessingException {
        return webService.getHandler().generateLatestInfections();
    }

    /**
     * Returns the number of total corona virus infections.
     * JsonProcessingException is to be handled on client side.
     *
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Override
    public String getTotalInfections() throws JsonProcessingException {
        return webService.getHandler().generateTrueInfections();
    }

    /**
     * Returns the number of the increase of corona virus infections in the last 24 hours.
     * JsonProcessingException is to be handled on client side.
     *
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Override
    public String getIncInfections24h() throws JsonProcessingException {
        return webService.getHandler().generateDailyInfectionsRaise();
    }

    /**
     * Returns the number of average increase calculated by days.
     * JsonProcessingException is to be handled on client side.
     *
     * @param days number required to calculate the average increase of corona virus infections
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Override
    public String getAverageInc(int days) throws JsonProcessingException {
        try {
            if (days > 1) {
                return webService.getHandler().generateAvgInfectionRaise(days);
            } else {
                return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(400, "Client sent an invalid request"));
            }
        } catch (Exception e) {
            return webService.getJacksonObjMapper().objectToString(new ErrorResponseModel(500, "A generic error occurred on the server"));
        }
    }

    /**
     * Returns the current incidence value.
     * JsonProcessingException is to be handled on client side.
     *
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Override
    public String getIncidence() throws JsonProcessingException {
        return webService.getHandler().generateIncidences();
    }

    /**
     * Returns the number of target infections.
     * JsonProcessingException is to be handled on client side.
     *
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Override
    public String getTargetInfections() throws JsonProcessingException {
        return webService.getHandler().generateTargetInfections();
    }

    /**
     * Returns the number of predicted days in lockdown until a certain incidence is reached
     * JsonProcessingException is to be handled on client side.
     *
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Override
    public String getForecastLockdown() throws JsonProcessingException {
        return webService.getHandler().generateForecast();
    }
}