import hrw.webservice.soap.Covid19SOAPWebService;
import hrw.webservice.soap.JsonProcessingException_Exception;
import hrw.webservice.soap.SoapServiceImplService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The SoapServiceTest class is intended for testing SOAP-based remote web calls only.
 * It requires a working wsdl-based client.jar file to connect to a remote server.
 *
 * @author Matthias Klaeßen
 * @version 1.0
 * @since 2021-02-16
 */
public class SoapServiceTest {

    /**
     * The Servicename
     */
    private static SoapServiceImplService c19service;

    /**
     * The webservice name defined by the interface
     */
    private static Covid19SOAPWebService port;

    /**
     * Class-wide string variable to prevent the need to be newly created every test instance.
     */
    private String command = "";

    /**
     * This method connects to a remote server running a SOAP-based web service.
     * It must be called before any tests are run.
     */
    @BeforeAll
    public static void initConnection() {
        c19service = new SoapServiceImplService();
        port = c19service.getSoapServiceImplPort();
    }

    /**
     * This method fetches all data provided by the server and checks whether all returned tags are present and no internal error occurred.
     *
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Test
    @DisplayName("SOAP: Total Data JH_RKI")
    public void checkTotalDataJHRKI() throws JsonProcessingException_Exception {

        command = port.totalDataJhRki(1);
        System.out.println("Test 1: Total Data JH_RKI - " + command);

        assert (!command.contains("\"status\":400") &&
                command.contains("newInfections24H") &&
                command.contains("totalInfections") &&
                command.contains("raisedInfections") &&
                command.contains("averageRaise") &&
                command.contains("incidence") &&
                command.contains("targetInfection") &&
                command.contains("remainingLockdown"));
    }

    /**
     * This method fetches data of the new infections of the last 24 hours provided by the server and checks whether all returned tags are present and no internal error occurred.
     *
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Test
    @DisplayName("SOAP: New Infections Last 24 hours")
    public void checkNewInfectionsLast24h() throws JsonProcessingException_Exception {

        command = port.newInfectionsLast24H();
        System.out.println("Test 2: New Infections Last 24 hours - " + command);

        assert (!command.contains("\"status\":400") && command.contains("newInfections24H"));
    }

    /**
     * This method fetches data of the total infections provided by the server and checks whether all returned tags are present and no internal error occurred.
     *
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Test
    @DisplayName("SOAP: Total Infections")
    public void checkTotalInfections() throws JsonProcessingException_Exception {

        command = port.totalInfections();
        System.out.println("Test 3: Total Infections - " + command);

        assert (!command.contains("\"status\":400") && command.contains("totalInfections"));
    }

    /**
     * This method fetches data of the increase of infections within the last 24 hours provided by the server and checks whether all returned tags are present and no internal error occurred.
     *
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Test
    @DisplayName("SOAP: Increase Infections 24 hours")
    public void checkIncreaseInfections24h() throws JsonProcessingException_Exception {

        command = port.increaseInfections24H();
        System.out.println("Test 4: Increase Infections 24 hours - " + command);

        assert (!command.contains("\"status\":400") && command.contains("raisedInfections"));
    }

    /**
     * This method fetches data of the average increase of infections within a defined number of days provided by the server and checks whether all returned tags are present and no internal error occurred.
     *
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Test
    @DisplayName("SOAP: Average Increase by Days")
    public void checkAverageIncrease() throws JsonProcessingException_Exception {

        int days = 5;
        command = port.averageIncreaseByDays(days);
        System.out.println("Test 5: Average Increase by Days (" + days + ") - " + command);

        assert (!command.contains("\"status\":400") && command.contains("averageRaise"));
    }

    /**
     * This method fetches data of the current incidence provided by the server and checks whether all returned tags are present and no internal error occurred.
     *
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Test
    @DisplayName("SOAP: Incidence")
    public void checkIncidence() throws JsonProcessingException_Exception {

        command = port.incidence();
        System.out.println("Test 6: Incidence - " + command);

        assert (!command.contains("\"status\":400") && command.contains("incidence"));
    }

    /**
     * This method fetches data of the target infections provided by the server and checks whether all returned tags are present and no internal error occurred.
     *
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Test
    @DisplayName("SOAP: Target Infections")
    public void checkTargetInfections() throws JsonProcessingException_Exception {

        command = port.targetInfections();
        System.out.println("Test 7: Target Infections - " + command);

        assert (!command.contains("\"status\":400") && command.contains("targetInfection"));
    }

    /**
     * This method fetches data of the predicted days of lockdown provided by the server and checks whether all returned tags are present and no internal error occurred.
     *
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Test
    @DisplayName("SOAP: Forecast Lockdown")
    public void checkForecastLockdown() throws JsonProcessingException_Exception {

        command = port.forecastDaysUntilRNumber();
        System.out.println("Test 8: Forecast Lockdown - " + command);

        assert (!command.contains("\"status\":400") && command.contains("remainingLockdown"));
    }

    /**
     * This method fetches data of the average increase of infections within a defined number of negative days provided by the server and checks whether all returned tags are present and no internal error occurred.
     *
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     */
    @Test
    @DisplayName("SOAP: Average Increase by Negative Days")
    public void checkNegativeAverageIncrease() throws JsonProcessingException_Exception {

        int days = -5;
        command = port.averageIncreaseByDays(days);
        System.out.println("Test 9: Average Increase by Negative Days (" + days + ") - " + command);

        assert ((command.contains("\"status\":400")));
    }
}
