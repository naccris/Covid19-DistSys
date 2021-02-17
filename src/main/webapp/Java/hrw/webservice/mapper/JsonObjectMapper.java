package hrw.webservice.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * The Object Mapper class contains methods to extend various methods of the Jackson Mapper.
 *
 * @author Lars Karbach
 * @version 1.0
 * @since 2021-02-16
 */
public class JsonObjectMapper {

    /**
     * Class variable of the Jackson object mapper to perform serialization and deserialization.
     */
    private ObjectMapper objectMapper;

    /**
     * Constructor to create a JSON object mapper.
     */
    public JsonObjectMapper() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Basic getter to return the object mapper
     *
     * @return ObjectMapper reference
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Basic setter to set ObjectMapper
     *
     * @param objectMapper passed ObjectMapper
     */
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Converts an object to a JSON string
     *
     * @param obj Object to be converted to JSON.
     * @return Object as JSON string
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 serialization/deserialization.
     */
    public String objectToString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * Deserializes input stream to JSON
     *
     * @param inputStream input stream which contains string object
     * @return deserialized JSON string
     * @throws IOException Throws an IO exception if an error occurs while deserializing the JSON.
     */
    public String deserializeJson(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream, String.class);
    }

    /**
     * Gets node from input stream
     *
     * @param inputStream input stream which contains string object
     * @return json node
     * @throws IOException Throws an IO exception if an error occurs while reading the JSON tree.
     */
    public JsonNode getNodeFromInputStream(InputStream inputStream) throws IOException {
        JsonNode node = objectMapper.readTree(inputStream);
        return node;
    }

    /**
     * Converts the InputStream parameter to a JSON node. Returns a sub node based on the name.
     *
     * @param inputStream Contains the JSON tree
     * @param name        Based on the name, the required node is returned.
     * @return needed JsonNode
     * @throws IOException Throws an IO exception if an error occurs while reading the JSON tree.
     */
    public JsonNode getSpecificNodeFromInputStream(InputStream inputStream, String name) throws IOException {
        JsonNode node = objectMapper.readTree(inputStream);
        node = node.get(name);

        return node;
    }

    /**
     * Method converts the map with string and integer values passed as parameters to a JSON string.
     *
     * @param valueMap The HashMap to be converted.
     * @return JSON string.
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 serialization/deserialization.
     */
    public String getJsonFromIntMap(HashMap<String, Integer> valueMap) throws JsonProcessingException {
        return objectMapper.writeValueAsString(valueMap);
    }

    /**
     * Method converts the map with string and double values passed as parameters to a JSON string.
     *
     * @param valueMap The HashMap to be converted.
     * @return JSON string.
     * @throws JsonProcessingException Throws a JSON processing exception if an error occurs during
     *                                 serialization/deserialization.
     */
    public String getJsonFromDoubleMap(HashMap<String, Double> valueMap) throws JsonProcessingException {
        return objectMapper.writeValueAsString(valueMap);
    }
}