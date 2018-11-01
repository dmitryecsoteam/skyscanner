package su.ecso.skyscanner.repositories;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import su.ecso.skyscanner.dto.Travels;

import static org.junit.Assert.assertTrue;

/**
 * Created by Dmitry on 01.11.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelsDAOImplTest {

    @Autowired
    TravelsDAO travels;

    @Autowired
    MongoTemplate mongoTemplate;

    @Before
    public void setUp() {
        Document travelToSave = new Document();
        travelToSave
                .append("_id", "testID")
                .append("origin", 0)
                .append("destination", 0)
                .append("date", "2000-01-01")
                .append("priceAirplane", 10250)
                .append("otherAttribute", "test value");
        mongoTemplate.save(travelToSave, "travels");
    }

    @Test
    public void testUpdateTravel() throws Exception {
        Travels travel = new Travels(0, 0, "2000-01-01", 1111);
        travels.updateTravel(travel);

        Document resultToMatch = new Document();
        resultToMatch
                .append("_id", "testID")
                .append("origin", 0)
                .append("destination", 0)
                .append("date", "2000-01-01")
                .append("priceAirplane", 1111)
                .append("otherAttribute", "test value");
        Document result = mongoTemplate.findById("testID", Document.class, "travels");
        assertTrue(result.equals(resultToMatch));
    }
}