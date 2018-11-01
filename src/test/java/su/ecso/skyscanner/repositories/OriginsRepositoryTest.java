package su.ecso.skyscanner.repositories;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import su.ecso.skyscanner.dto.Origins;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dmitry on 01.11.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OriginsRepositoryTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private OriginsRepository originsTest;

    @Before
    public void setUp() {
        Document originToSave1 = new Document();
        originToSave1
                .append("_id", 0)
                .append("iata", "QQQ")
                .append("priceAirplaneService", "aviasales");
        mongoTemplate.save(originToSave1, "origins");

        Document originToSave2 = new Document();
        originToSave2
                .append("_id", 1)
                .append("iata", "WWW")
                .append("priceAirplaneService", "skyscanner");
        mongoTemplate.save(originToSave2, "origins");
    }

    @Test
    public void testFindByPriceAirplaneService() throws Exception {
        List<Origins> result = originsTest.findByPriceAirplaneService("skyscanner");
        assertEquals(1, result.size());
    }
}