package hrw.webservice.soap;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * The SoapServiceInterface interface defines the functions available for a SOAP based web service.
 * All methods annotated with @WebMethod are legal method calls within the client.
 * This interface exists to guarantee a streamlined implementation of all methods required by a client for possibly multiple data sources.
 *
 * @author Matthias Klaeßen
 * @version 1.0
 * @since 2021-02-16
 */
@WebService(name = "Covid19SOAPWebService")
public interface SoapServiceInterface {

    /**
     * Returns combined results of calculations based on data provided by John Hopkins University and Robert-Koch-Institute.
     * JsonProcessingException is to be handled on client side.
     *
     * @param days number required by average increase
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @WebMethod(operationName = "total-data-jh-rki")
    String getTotalDataJHRKI(@WebParam(name = "days") int days) throws JsonProcessingException;

    /**
     * Returns the number of new corona virus infections of the last 24 hours.
     * JsonProcessingException is to be handled on client side.
     *
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @WebMethod(operationName = "new-infections-last-24h")
    String getNewInfectionsLast24h() throws JsonProcessingException;

    /**
     * Returns the number of total corona virus infections.
     * JsonProcessingException is to be handled on client side.
     *
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @WebMethod(operationName = "total-infections")
    String getTotalInfections() throws JsonProcessingException;

    /**
     * Returns the number of the increase of corona virus infections in the last 24 hours.
     * JsonProcessingException is to be handled on client side.
     *
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @WebMethod(operationName = "increase-infections-24h")
    String getIncInfections24h() throws JsonProcessingException;

    /**
     * Returns the number of average increase calculated by days.
     * JsonProcessingException is to be handled on client side.
     *
     * @param days number required to calculate the average increase of corona virus infections
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @WebMethod(operationName = "average-increase-by-days")
    String getAverageInc(@WebParam(name = "days") int days) throws JsonProcessingException;

    /**
     * Returns the current incidence value.
     * JsonProcessingException is to be handled on client side.
     *
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @WebMethod(operationName = "incidence")
    String getIncidence() throws JsonProcessingException;

    /**
     * Returns the number of target infections.
     * JsonProcessingException is to be handled on client side.
     *
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @WebMethod(operationName = "target-infections")
    String getTargetInfections() throws JsonProcessingException;

    /**
     * Returns the number of predicted days in lockdown until a certain incidence is reached
     * JsonProcessingException is to be handled on client side.
     *
     * @return String in JSON format
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @WebMethod(operationName = "forecast-days-until-r-number")
    String getForecastLockdown() throws JsonProcessingException;
}
