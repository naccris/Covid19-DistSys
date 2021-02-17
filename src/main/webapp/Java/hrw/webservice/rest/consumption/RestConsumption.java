package hrw.webservice.rest.consumption;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Consumption for the REST Services
 *
 * @author Lars Karbach
 * @version 1.0
 * @since 2021-02-16
 */

public class RestConsumption {

    /**
     * @param url the needed URL for RKI and JH
     * @return response stream
     * @throws IOException Throws an IO exception if an error occurs while reading the JSON tree.
     */
    public InputStream sendRestRequest(String url) throws IOException {
        try {
            InputStream responseStream;
            URL restEndpoint = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) restEndpoint.openConnection();
            connection.setRequestProperty("accept", "application/json"); // Request Property regulation for JSON

            responseStream = connection.getInputStream();

            return responseStream;
        } catch (Exception ex) {
            return null;
        }
    }
}
