package su.ecso.skyscanner.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

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
public class SkyscannerApiServiceTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    SkyscannerApiService api;

    private MockRestServiceServer mockServer;

    private String RESPONSE = "{\"Routes\":[],\"Quotes\":[{\"QuoteId\":1,\"MinPrice\":77.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[1464],\"OriginId\":56756,\"DestinationId\":86562,\"DepartureDate\":\"2018-12-01T00:00:00\"},\"QuoteDateTime\":\"2018-11-01T09:06:00\"},{\"QuoteId\":2,\"MinPrice\":63.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[883],\"OriginId\":56756,\"DestinationId\":86562,\"DepartureDate\":\"2018-12-02T00:00:00\"},\"QuoteDateTime\":\"2018-11-01T09:21:00\"},{\"QuoteId\":3,\"MinPrice\":63.0,\"Direct\":true,\"OutboundLeg\":{\"CarrierIds\":[883],\"OriginId\":56756,\"DestinationId\":86562,\"DepartureDate\":\"2018-12-03T00:00:00\"},\"QuoteDateTime\":\"2018-11-01T09:21:00\"}],\"Places\":[{\"PlaceId\":56756,\"IataCode\":\"HND\",\"Name\":\"Tokyo Haneda\",\"Type\":\"Station\",\"SkyscannerCode\":\"HND\",\"CityName\":\"Tokyo\",\"CityId\":\"TYOA\",\"CountryName\":\"Japan\"},{\"PlaceId\":86562,\"IataCode\":\"UKB\",\"Name\":\"Kobe\",\"Type\":\"Station\",\"SkyscannerCode\":\"UKB\",\"CityName\":\"Kobe\",\"CityId\":\"JUKB\",\"CountryName\":\"Japan\"}],\"Carriers\":[{\"CarrierId\":883,\"Name\":\"Skymark Airlines\"},{\"CarrierId\":1464,\"Name\":\"ANA\"}],\"Currencies\":[{\"Code\":\"USD\",\"Symbol\":\"$\",\"ThousandsSeparator\":\",\",\"DecimalSeparator\":\".\",\"SymbolOnLeft\":true,\"SpaceBetweenAmountAndSymbol\":false,\"RoundingCoefficient\":0,\"DecimalDigits\":2}]}";
    private String URL = "http://api.travelpayouts.com";

    @Before
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetPrices() throws Exception {
        mockServer.expect(once(), requestTo(URL))
                .andRespond(withSuccess(RESPONSE, MediaType.APPLICATION_JSON));

        List<Price> pricesResult = api.getPrices(URL);

        assertEquals(77, pricesResult.get(0).getValue());
        assertEquals("2018-12-01", pricesResult.get(0).getDepart_date());
    }
}