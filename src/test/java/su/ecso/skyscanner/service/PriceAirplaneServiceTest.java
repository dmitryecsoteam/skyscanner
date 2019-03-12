package su.ecso.skyscanner.service;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import su.ecso.skyscanner.repositories.DestinationsRepository;
import su.ecso.skyscanner.repositories.OriginsRepository;
import su.ecso.skyscanner.repositories.TravelsDAO;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by Dmitry on 01.11.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceAirplaneServiceTest {

    @Value("${numberOfMonths}")
    private int numberOfMonths;

    @Autowired
    PriceAirplaneService service;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RestTemplate restTemplate;



    @Autowired
    TravelsDAO travels;

    @Autowired
    OriginsRepository origins;

    @Autowired
    DestinationsRepository destinations;

    private MockRestServiceServer mockServer;

    private String RESPONSE = "{\"Routes\":[],\"Quotes\":[{\"QuoteId\":1,\"MinPrice\":77.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1464],\"OriginId\":56756,\"DestinationId\":86562,\"DepartureDate\":\"2000-01-05T00:00:00\"},\"QuoteDateTime\":\"2018-11-01T09:06:00\"},{\"QuoteId\":2,\"MinPrice\":63.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[883],\"OriginId\":56756,\"DestinationId\":86562,\"DepartureDate\":\"2000-01-06T00:00:00\"},\"QuoteDateTime\":\"2018-11-01T09:21:00\"}]}";

    private String EMPTY_RESPONSE = "{\"Routes\":[],\"Quotes\":[]}";

    @Before
    public void setUp() {
        Document travelToSave1 = new Document();
        travelToSave1
                .append("_id", "testID1")
                .append("origin", 0)
                .append("destination", 0)
                .append("date", "2000-01-05")
                .append("priceAirplane", 10250)
                .append("otherAttribute", "test value 1");
        mongoTemplate.save(travelToSave1, "travels");

        Document travelToSave2 = new Document();
        travelToSave2
                .append("_id", "testID2")
                .append("origin", 0)
                .append("destination", 0)
                .append("date", "2000-01-06")
                .append("otherAttribute", "test value 2");
        mongoTemplate.save(travelToSave2, "travels");

        Document originToSave = new Document();
        originToSave
                .append("_id", 0)
                .append("iata", "IAT")
                .append("priceAirplaneService", "skyscanner");
        mongoTemplate.save(originToSave, "origins");

        Document destinationToSave = new Document();
        destinationToSave
                .append("_id", 0)
                .append("iata", "XXX");
        mongoTemplate.save(destinationToSave, "destinations");

        //mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true).build();
    }

    @Test
    public void testProcessTravels() throws Exception {
        String firstURL = service.generateURL("IAT", "XXX", "2000-01-03");
        mockServer.expect(once(), requestTo(firstURL))
               .andRespond(withSuccess(RESPONSE, MediaType.APPLICATION_JSON));

        for (int i = 2; i <= numberOfMonths; i++) {
            String url = service.generateURL("IAT", "XXX", "2000-0" + i + "-01");
            mockServer.expect(once(), requestTo(url))
                    .andRespond(withSuccess(EMPTY_RESPONSE, MediaType.APPLICATION_JSON));
        }

        service.processTravels(LocalDate.of(2000, 1, 3));

        Document result1 = mongoTemplate.findById("testID1", Document.class, "travels");
        Document result2 = mongoTemplate.findById("testID2", Document.class, "travels");

        Document expectedResult1 = new Document();
        expectedResult1
                .append("_id", "testID1")
                .append("origin", 0)
                .append("destination", 0)
                .append("date", "2000-01-05")
                .append("priceAirplane", 77)
                .append("otherAttribute", "test value 1");
        Document expectedResult2 = new Document();
        expectedResult2
                .append("_id", "testID2")
                .append("origin", 0)
                .append("destination", 0)
                .append("date", "2000-01-06")
                .append("priceAirplane", 63)
                .append("otherAttribute", "test value 2");

        assertEquals(expectedResult1, result1);
        assertEquals(expectedResult2, result2);
    }

    @Test
    public void testGenerateURL() throws Exception {
        String resultUrl = service.generateURL("TYO", "NGS", "2018-11-01");
        String expectedUrl = "http://partners.api.skyscanner.net/apiservices/browseroutes/v1.0/JP/usd/en-US/TYO/NGS/2018-11-01/?apikey=prtl6749387986743898559646983194";
        assertEquals(expectedUrl, resultUrl);
    }

    @Test
    public void testGenerateListOfMonths() throws Exception {
        LocalDate date = LocalDate.of(2018, 3, 15);
        LocalDate lastDate = LocalDate.of(2018, 3 + numberOfMonths - 1, 1);
        List<String> months = service.generateListOfMonths(date);
        assertEquals("2018-03-15", months.get(0));
        assertEquals(lastDate.toString(), months.get(months.size() - 1));
    }
}