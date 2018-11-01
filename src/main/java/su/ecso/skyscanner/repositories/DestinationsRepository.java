package su.ecso.skyscanner.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import su.ecso.skyscanner.dto.Destinations;

/**
 * Created by Dmitry on 07.10.2018.
 */
public interface DestinationsRepository extends MongoRepository<Destinations, Integer> {
}
