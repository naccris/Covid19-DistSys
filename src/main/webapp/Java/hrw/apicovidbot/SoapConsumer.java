package hrw.apicovidbot;

import hrw.webservice.soap.Covid19SOAPWebService;
import hrw.webservice.soap.JsonProcessingException_Exception;
import hrw.webservice.soap.SoapServiceImplService;
import org.json.JSONObject;

/**
 * The SoapConsumer class acts as a client to receive data from the SOAP-API.
 * This class makes use of the deployed SOAP-wsdl libraries, which have been imported to this project as a .jar-file.
 *
 * @author Daniel Muschiol
 * @version 1.0
 * @since 2021-02-16
 */
public class SoapConsumer {

    /**
     * The port of the SOAP-API
     */
    Covid19SOAPWebService port;

    /**
     * The constructor of the SoapConsumer class instantiates the SOAP service making use of the imported .jar-file.
     */
    public SoapConsumer() {
        SoapServiceImplService soapService = new SoapServiceImplService();
        this.port = soapService.getSoapServiceImplPort();
    }

    /**
     * This method calls the SOAP method to receive the requested value (incidence rate)
     *
     * @return a JSONObject holding the SOAP response (incidence rate)
     */
    public JSONObject getIncidence() {

        try {
            System.out.println(port.incidence());
            JSONObject json = new JSONObject(port.incidence());
            return json;
        } catch (JsonProcessingException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method calls the SOAP method to receive the requested value (average increase for n days)
     *
     * @param days number of days to calculate the average increase
     * @return a JSONObject holding the SOAP response (average increase for n days)
     */
    public JSONObject getAverageInc(int days) {
        try {
            System.out.println(port.averageIncreaseByDays(days));
            JSONObject json = new JSONObject(port.averageIncreaseByDays(days));
            return json;
        } catch (JsonProcessingException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method calls the SOAP method to receive the requested value (infections of last 24h)
     *
     * @return a JSONObject holding the SOAP response (infections of last 24h)
     */
    public JSONObject getIncInfection24h() {
        try {
            System.out.println(port.increaseInfections24H());
            JSONObject json = new JSONObject(port.increaseInfections24H());
            return json;
        } catch (JsonProcessingException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method calls the SOAP method to receive the requested value (forecast of remaining lockdown days)
     *
     * @return a JSONObject holding the SOAP response (forecast of remaining lockdown days)
     */
    public JSONObject getForecastLockdown() {
        try {
            System.out.println(port.forecastDaysUntilRNumber());
            JSONObject json = new JSONObject(port.forecastDaysUntilRNumber());
            return json;
        } catch (JsonProcessingException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method calls the SOAP method to receive the requested value (new infections of last 24h)
     *
     * @return a JSONObject holding the SOAP response (new infections of last 24h)
     */
    public JSONObject getNewInfectionsLast24h() {
        try {
            System.out.println(port.newInfectionsLast24H());
            JSONObject json = new JSONObject(port.newInfectionsLast24H());
            return json;
        } catch (JsonProcessingException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method calls the SOAP method to receive the requested value (target infections)
     *
     * @return a JSONObject holding the SOAP response (target infections)
     */
    public JSONObject getTargetInfections() {
        try {
            System.out.println(port.targetInfections());
            JSONObject json = new JSONObject(port.targetInfections());
            return json;
        } catch (JsonProcessingException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method calls the SOAP method to receive the requested value (total infections)
     *
     * @return a JSONObject holding the SOAP response (total infections)
     */
    public JSONObject getTotalInfections() {
        try {
            System.out.println(port.totalInfections());
            JSONObject json = new JSONObject(port.totalInfections());
            return json;
        } catch (JsonProcessingException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method calls the SOAP method to receive the requested value (total data)
     *
     * @param days number of days to calculate the average increase (which is part of total data)
     * @return a JSONObject holding the SOAP response (total data)
     */
    public JSONObject getTotalData(int days) {
        try {
            JSONObject json = new JSONObject(port.totalDataJhRki(days));
            return json;
        } catch (JsonProcessingException_Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

