package hrw.apicovidbot;

import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * The RestConsumer class acts as a client to receive data from the REST-API.
 *
 * @author Daniel Muschiol
 * @version 1.0
 * @since 2021-02-16
 */
public class RestConsumer {

    /**
     * A string holding the to-be-called REST-API url.
     */
    String url;

    /**
     * @param url holding the to-be-called REST-API url.
     */
    public RestConsumer(String url) {
        this.url = url;
    }

    /**
     * @return a JSONObject containing the REST-API response.
     */
    public JSONObject getCovidStats() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        JSONObject json = new JSONObject(target.request(MediaType.APPLICATION_JSON).get(String.class));
        client.close();
        return json;
    }

}