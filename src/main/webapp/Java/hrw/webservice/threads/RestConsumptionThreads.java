package hrw.webservice.threads;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import hrw.webservice.logic.Covid19WebService;
import hrw.webservice.model.Attributes;
import hrw.webservice.model.JohnHDailyInfos;
import hrw.webservice.rest.consumption.RestConsumption;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Consumes RKI and John Hopkins Data and saves them in Lists.
 *
 * @author Lars Karbach
 * @version 1.0
 * @since 2021-02-16
 */
public class RestConsumptionThreads extends TimerTask {
    private final Covid19WebService webService;
    private final RestConsumption restConsumption;

    /**
     * Constructor to create the class that takes care of consuming the data
     *
     * @param restConsumption Object reference that performs queries to the individual institutes
     * @param webService      Object reference that initializes the program
     */
    public RestConsumptionThreads(RestConsumption restConsumption, Covid19WebService webService) {
        this.restConsumption = restConsumption;
        this.webService = webService;
    }

    /**
     * Thread that takes over the query and storage of the institute data
     */
    @Override
    public void run() {
        JsonNode tmpNode;
        JsonNode itemNode;
        List<JohnHDailyInfos> johnHDailyList;
        List<Attributes> rkiDailyList;

        try {

            InputStream inputStream = restConsumption.sendRestRequest("https://pomber.github.io/covid19/timeseries.json");

            tmpNode = webService.getJacksonObjMapper().getSpecificNodeFromInputStream(inputStream, "Germany");

            JavaType type = webService.getJacksonObjMapper().getObjectMapper().getTypeFactory().
                    constructCollectionType(List.class, JohnHDailyInfos.class);

            johnHDailyList = webService.getJacksonObjMapper().getObjectMapper().convertValue(tmpNode, type);

            webService.setJHDailyInfosList(johnHDailyList);
            webService.getCalcJHKeyFigures().updateKeyFigures(webService.getJHDailyInfosList());

            inputStream = restConsumption.sendRestRequest("https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/Coronaf%C3%A4lle_in_den_Bundesl%C3%A4ndern/FeatureServer/0/query?where=1%3D1&outFields=cases7_bl,Fallzahl,LAN_ew_EWZ&returnGeometry=false&outSR=4326&f=json");

            tmpNode = webService.getJacksonObjMapper().getSpecificNodeFromInputStream(inputStream, "features");

            rkiDailyList = new ArrayList<>();

            for (int i = 0; i < 16; i++) {
                itemNode = tmpNode.get(i).get("attributes");
                rkiDailyList.add(new Attributes(itemNode.get("cases7_bl").asInt(), itemNode.get("Fallzahl").asInt(), itemNode.get("LAN_ew_EWZ").asInt()));
            }
            webService.getRkiTotalData().add(webService.getCalcRKIKeyFigures().preCalcRkiValues(rkiDailyList));

        } catch (IOException ioException) {
            System.out.println("An exception occurred when trying to consume endpoints!");
        }
    }
}