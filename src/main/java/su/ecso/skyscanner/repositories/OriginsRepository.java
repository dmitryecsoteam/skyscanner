package su.ecso.skyscanner.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import su.ecso.skyscanner.dto.Origins;

import java.util.List;

/**
 * Created by Dmitry on 07.10.2018.
 */
public interface OriginsRepository extends MongoRepository<Origins, Integer> {
    List<Origins> findByPriceAirplaneService(String name);
}
