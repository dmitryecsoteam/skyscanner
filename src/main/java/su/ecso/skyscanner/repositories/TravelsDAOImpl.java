package su.ecso.skyscanner.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import su.ecso.skyscanner.dto.Travels;

/**
 * Created by Dmitry on 07.10.2018.
 */
@Repository
public class TravelsDAOImpl implements TravelsDAO {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public TravelsDAOImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

//    @Override
//    public Travels findTravel(int origin, int destination, String date) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("origin").is(origin));
//        query.addCriteria(Criteria.where("destination").is(destination));
//        query.addCriteria(Criteria.where("date").is(date));
//        return mongoTemplate.findOne(query , Travels.class);
//    }

    @Override
    public void updateTravel(Travels travel) {
        Query query = new Query();
        //query.addCriteria(Criteria.where("id").is(travel.getId()));
        query.addCriteria(Criteria.where("origin").is(travel.getOrigin()))
                .addCriteria(Criteria.where("destination").is(travel.getDestination()))
                .addCriteria(Criteria.where("date").is(travel.getDate()));
        Update update = new Update();
        update.set("priceAirplane", travel.getPriceAirplane());

        mongoTemplate.updateFirst(query, update, Travels.class);
    }

}
