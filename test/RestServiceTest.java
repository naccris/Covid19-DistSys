import hrw.webservice.soap.JsonProcessingException_Exception;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * The RestServiceTest class is intended for testing Rest-based remote web calls only.
 *
 * @author Matthias Klaeßen, Philip Klein
 * @version 1.0
 * @since 2021-02-16
 */
public class RestServiceTest {

    private URL url;

    /**
     * This method tests the rest request for new infections in the last 24h
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     * @throws IOException Throws an IO exception if an error occurs while reading the JSON tree.
     */
    @Test
    @DisplayName("REST: New Infections Last 24 hours")
    public void checkNewInfectionsLast24h() throws JsonProcessingException_Exception, IOException {
        System.out.print("Test 1: New Infections Last 24 hours - ");
        url = new URL("http://tomcat.nxuz.de:8080/Covid19-DisSys/covid/data/infections?filter=latest");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String command = ""; (command = reader.readLine()) != null; ) {
                System.out.println(command);
                assert (!command.contains("\"status\":400") && command.contains("newInfections24H"));
            }
        }
    }

    /**
     * This method tests the rest request for total infections
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     * @throws IOException Throws an IO exception if an error occurs while reading the JSON tree.
     */
    @Test
    @DisplayName("REST: Total Infections")
    public void checkTotalInfections() throws JsonProcessingException_Exception, IOException {
        System.out.print("Test 2: New Infections Last 24 hours - ");
        url = new URL("http://tomcat.nxuz.de:8080/Covid19-DisSys/covid/data/infections?filter=total");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String command = ""; (command = reader.readLine()) != null; ) {
                System.out.println(command);
                assert (!command.contains("\"status\":400") && command.contains("totalInfections"));
            }
        }
    }


    /**
     * This method tests the rest request for the increased infections in 24h
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     * @throws IOException Throws an IO exception if an error occurs while reading the JSON tree.
     */
    @Test
    @DisplayName("REST: Increased Infections 24 hours")
    public void checkIncreaseInfections24h() throws JsonProcessingException_Exception, IOException {
        System.out.print("Test 3: Raised Infections Last 24 hours - ");
        url = new URL("http://tomcat.nxuz.de:8080/Covid19-DisSys/covid/data/infections/raised?days=1");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String command = ""; (command = reader.readLine()) != null; ) {
                System.out.println(command);
                assert (!command.contains("\"status\":400") && command.contains("raisedInfections"));
            }
        }
    }

    /**
     * This method tests the rest request for the average increase for n days
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     * @throws IOException Throws an IO exception if an error occurs while reading the JSON tree.
     */
    @Test
    @DisplayName("REST: Average Increase by Days")
    public void checkAverageIncrease() throws JsonProcessingException_Exception, IOException {
        System.out.print("Test 4: Average Raised Infections N - ");
        url = new URL("http://tomcat.nxuz.de:8080/Covid19-DisSys/covid/data/infections/raised?days=5");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String command = ""; (command = reader.readLine()) != null; ) {
                System.out.println(command);
                assert (!command.contains("\"status\":400") && command.contains("averageRaise"));
            }
        }
    }

    /**
     * This method tests the rest request for the incidence value
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     * @throws IOException Throws an IO exception if an error occurs while reading the JSON tree.
     */
    @Test
    @DisplayName("REST: Incidence")
    public void checkIncidence() throws JsonProcessingException_Exception, IOException {
        System.out.print("Test 5: Incidences Germany - ");
        url = new URL("http://tomcat.nxuz.de:8080/Covid19-DisSys/covid/data/incidences");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String command = ""; (command = reader.readLine()) != null; ) {
                System.out.println(command);
                assert (!command.contains("\"status\":400") && command.contains("incidence"));
            }
        }
    }

    /**
     * This method tests the rest request for target infections
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     * @throws IOException Throws an IO exception if an error occurs while reading the JSON tree.
     */
    @Test
    @DisplayName("REST: Target Infections")
    public void checkTargetInfections() throws JsonProcessingException_Exception, IOException {
        System.out.print("Test 6: Target Infections Germany - ");
        url = new URL("http://tomcat.nxuz.de:8080/Covid19-DisSys/covid/data/infections?filter=target");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String command = ""; (command = reader.readLine()) != null; ) {
                System.out.println(command);
                assert (!command.contains("\"status\":400") && command.contains("targetInfection"));
            }
        }
    }

    /**
     * This method tests the rest request for the remaining lockdown forecast
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     * @throws IOException Throws an IO exception if an error occurs while reading the JSON tree.
     */
    @Test
    @DisplayName("REST: Forecast Lockdown")
    public void checkForecastLockdown() throws JsonProcessingException_Exception, IOException {
        System.out.print("Test 7: Remaining Lockdown Forecast - ");
        url = new URL("http://tomcat.nxuz.de:8080/Covid19-DisSys/covid/data/forecast");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String command = ""; (command = reader.readLine()) != null; ) {
                System.out.println(command);
                assert (!command.contains("\"status\":400") && command.contains("remainingLockdown"));
            }
        }
    }

    /**
     * This method tests the rest request for the average increase by negative days
     * @throws JsonProcessingException_Exception Throws a JSON processing exception if an error occurs during de-/serialization
     * @throws IOException Throws an IO exception if an error occurs while reading the JSON tree.
     */
    @Test
    @DisplayName("REST: Average Increase by Negative Days")
    public void checkNegativeAverageIncrease() throws JsonProcessingException_Exception, IOException {
        System.out.print("Negativ Test: New Infections Last 24 hours - ");
        url = new URL("http://tomcat.nxuz.de:8080/Covid19-DisSys/covid/data/infections/raised?days=-8");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String command = ""; (command = reader.readLine()) != null; ) {
                System.out.println(command);
                assert (command.contains("\"status\":400"));
            }
        }
    }
}
