import hrw.apicovidbot.SoapConsumer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The SoapConsumerTest class is intended for testing SOAP-based remote web calls only.
 * It requires a working wsdl-based client.jar file to connect to a remote server.
 *
 * @author Daniel Muschiol
 * @version 1.0
 * @since 2021-02-16
 */
public class SoapConsumerTest {

    /**
     * An instance of SoapConsumer
     */
    private static SoapConsumer soapConsumer;


    /**
     * A string representing the SOAP service response
     */
    private static String response;

    /**
     * This method instantiates the SoapConsumer class
     * It must be called before any tests are run.
     */
    @BeforeAll
    public static void initCreation() {
        soapConsumer = new SoapConsumer();
    }

    /**
     * This method fetches all data provided by the SOAP-API.
     */
    @Test
    @DisplayName("SOAP Consumer: Total Data")
    public void checkTotalData() {

        int days = 20;  // Testing the calculation for days = 20
        response = soapConsumer.getTotalData(days).toString();
        System.out.println("Test 1: Total Data - " + response);

        assert (response.contains("newInfections24H") &&
                response.contains("totalInfections") &&
                response.contains("raisedInfections") &&
                response.contains("averageRaise") &&
                response.contains("incidence") &&
                response.contains("targetInfection") &&
                response.contains("remainingLockdown"));
    }

    /**
     * This method fetches data regarding the new infections in the last 24h provided by the SOAP-API.
     */
    @Test
    @DisplayName("SOAP  Consumer: New Infections Last 24 hours")
    public void checkNewInfectionsLast24h() {

        response = soapConsumer.getNewInfectionsLast24h().toString();
        System.out.println("Test 2: New Infections Last 24 hours - " + response);

        assert (response.contains("newInfections24H"));
    }

    /**
     * This method fetches data of the total infections provided by the SOAP-API.
     */
    @Test
    @DisplayName("SOAP Consumer: Total Infections")
    public void checkTotalInfections() {

        response = soapConsumer.getTotalInfections().toString();
        System.out.println("Test 3: Total Infections - " + response);

        assert (response.contains("totalInfections"));
    }

    /**
     * This method fetches data of the increase of infections within the last 24 hours provided by the SOAP-API.
     */
    @Test
    @DisplayName("SOAP Consumer: Increased Infections 24 hours")
    public void checkIncreaseInfections24h() {

        response = soapConsumer.getIncInfection24h().toString();
        System.out.println("Test 4: Increased Infections 24 hours - " + response);

        assert (response.contains("raisedInfections"));
    }

    /**
     * This method fetches data of the average increase of infections within a defined number of days provided by the SOAP-API.
     */
    @Test
    @DisplayName("SOAP Consumer: Average Increase by Days")
    public void checkAverageIncrease() {

        int days = 20;
        response = soapConsumer.getAverageInc(days).toString();
        System.out.println("Test 5: Average Increase by Days (" + days + ") - " + response);

        assert (response.contains("averageRaise"));
    }

    /**
     * This method fetches data of the current incidence provided by the SOAP-API.
     */
    @Test
    @DisplayName("SOAP Consumer: Incidence")
    public void checkIncidence() {

        response = soapConsumer.getIncidence().toString();
        System.out.println("Test 6: Incidence - " + response);

        assert (response.contains("incidence"));
    }

    /**
     * This method fetches data of the target infections provided by the SOAP-API.
     */
    @Test
    @DisplayName("SOAP Consumer: Target Infections")
    public void checkTargetInfections() {

        response = soapConsumer.getTargetInfections().toString();
        System.out.println("Test 7: Target Infections - " + response);

        assert (response.contains("targetInfection"));
    }

    /**
     * This method fetches data of the predicted days of lockdown provided by the SOAP-API.
     */
    @Test
    @DisplayName("SOAP Consumer: Forecast Lockdown")
    public void checkForecastLockdown() {

        response = soapConsumer.getForecastLockdown().toString();
        System.out.println("Test 8: Forecast Lockdown - " + response);

        assert (response.contains("remainingLockdown"));
    }
}
