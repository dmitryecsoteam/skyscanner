package su.ecso.skyscanner.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry on 08.10.2018.
 */
@Service
public class SkyscannerApiService {

    private static final Logger logger = LoggerFactory.getLogger(SkyscannerApiService.class);

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectReader OBJECT_READER = new ObjectMapper().readerFor(new TypeReference<List<Price>>() {});

    public List<Price> getPrices(String url) {
        String json = "";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            json = response.getBody();
            JsonNode root = OBJECT_READER.readTree(json);
            JsonNode data = root.path("Quotes");
            return OBJECT_READER.readValue(data);
        } catch (RestClientResponseException rcre) {
            logger.error("Request to url " + url + " was unsuccessfull. Response: " + rcre.getResponseBodyAsString());
            logger.error("", rcre);
        } catch (RestClientException rce) {
            logger.error("Request to url " + url + " was unsuccessfull.");
            logger.error("", rce);
        } catch (IOException ioe) {
            logger.error("Error while processing url " + url);
            logger.error("JSON: " + json);
            logger.error("", ioe);
        }
        return new ArrayList<>();
    }
}
