import hrw.apicovidbot.RestConsumer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the RestConsumer class
 *
 * @author Daniel Muschiol
 * @version 1.0
 * @since 2021-02-16
 */
public class RestConsumerTest {

    /**
     * A string representing the REST service response
     */
    private static String response;

    /**
     * A number of Strings representing the to-be-called REST urls
     */
    private static String rootUrl;
    private static String url1;
    private static String url2;
    private static String url3;
    private static String url4;
    private static String url5;
    private static String url6;
    private static String url7;
    private static String url8;

    /**
     * This method creates the REST-urls
     * It must be called before any tests are run.
     */
    @BeforeAll
    public static void createUrls() {

        rootUrl = "http://tomcat.nxuz.de:8080/Covid19-DisSys";
        url1 = rootUrl + "/covid/data/infections?filter=latest";
        url2 = rootUrl + "/covid/data/infections?filter=total";
        url3 = rootUrl + "/covid/data/infections/raised?days=1";
        url4 = rootUrl + "/covid/data/incidences";
        url5 = rootUrl + "/covid/data/infections?filter=target";
        url6 = rootUrl + "/covid/data/forecast";
        url7 = rootUrl + "/covid/data/infections/raised?days=10";
        url8 = rootUrl + "/covid/data?days=10";

    }

    /**
     * This method fetches data regarding the new infections in the last 24h provided by the REST-API.
     */
    @Test
    @DisplayName("REST: checkNewInfections24H")
    public void checkNewInfections24H() {

        response = new RestConsumer(url1).getCovidStats().toString();
        System.out.println("Test 1: New Infections 24H - " + response);
        assert (response.contains("newInfections24H"));

    }

    /**
     * This method fetches data regarding the total infections provided by the REST-API.
     */
    @Test
    @DisplayName("REST: checkTotalInfections")
    public void checkTotalInfections() {

        response = new RestConsumer(url2).getCovidStats().toString();
        System.out.println("Test 2: Total Infections - " + response);
        assert (response.contains("totalInfections"));

    }

    /**
     * This method fetches data regarding the raised infections provided by the REST-API.
     */
    @Test
    @DisplayName("REST: checkRaisedInfections")
    public void checkRaisedInfections() {

        response = new RestConsumer(url3).getCovidStats().toString();
        System.out.println("Test 3: Raised Infections - " + response);
        assert (response.contains("raisedInfections"));

    }

    /**
     * This method fetches data regarding the incidence value provided by the REST-API.
     */
    @Test
    @DisplayName("REST: checkIncidence")
    public void checkIncidence() {

        response = new RestConsumer(url4).getCovidStats().toString();
        System.out.println("Test 4: Incidences - " + response);
        assert (response.contains("incidence"));

    }

    /**
     * This method fetches data regarding the target infection provided by the REST-API.
     */
    @Test
    @DisplayName("REST: checkTargetInfection")
    public void checkTargetInfection() {

        response = new RestConsumer(url5).getCovidStats().toString();
        System.out.println("Test 5: Target Infection - " + response);
        assert (response.contains("targetInfection"));

    }

    /**
     * This method fetches data regarding the remaining lockdown forecast provided by the REST-API.
     */
    @Test
    @DisplayName("REST: checkRemainingLockdown")
    public void checkRemainingLockdown() {

        response = new RestConsumer(url6).getCovidStats().toString();
        System.out.println("Test 6: Remaining Lockdown Forecast - " + response);
        assert (response.contains("remainingLockdown"));

    }

    /**
     * This method fetches data regarding the average raise over a given period of time provided by the REST-API.
     */
    @Test
    @DisplayName("REST: checkAverageRaise")
    public void checkAverageRaise() {

        response = new RestConsumer(url7).getCovidStats().toString();
        System.out.println("Test 7: Average Raise - " + response);
        assert (response.contains("averageRaise"));

    }

    /**
     * This method fetches all data provided by the REST-API.
     */
    @Test
    @DisplayName("REST: checkTotalData")
    public void checkTotalData() {

        response = new RestConsumer(url8).getCovidStats().toString();
        System.out.println("Test 8: Total Data - " + response);
        assert (response.contains("newInfections24H") &&
                response.contains("totalInfections") &&
                response.contains("raisedInfections") &&
                response.contains("incidence") &&
                response.contains("targetInfection") &&
                response.contains("remainingLockdown") &&
                response.contains("averageRaise"));

    }
}
