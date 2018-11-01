package su.ecso.skyscanner.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import su.ecso.skyscanner.api.Price;
import su.ecso.skyscanner.api.SkyscannerApiService;
import su.ecso.skyscanner.dto.Destinations;
import su.ecso.skyscanner.dto.Origins;
import su.ecso.skyscanner.dto.Travels;
import su.ecso.skyscanner.repositories.DestinationsRepository;
import su.ecso.skyscanner.repositories.OriginsRepository;
import su.ecso.skyscanner.repositories.TravelsDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry on 09.10.2018.
 */

@Service
public class PriceAirplaneService {

    private static final Logger logger = LoggerFactory.getLogger(PriceAirplaneService.class);

    @Autowired
    private OriginsRepository origins;

    @Autowired
    private DestinationsRepository destinations;

    @Autowired
    private TravelsDAO travels;

    @Autowired
    private SkyscannerApiService api;

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${token}")
    private String token;

    @Value("${numberOfMonths}")
    private int numberOfMonths;


    public void processTravels(LocalDate today) {

        logger.info("Starting processing travels");

        // Generate List of first days of months
        List<String> months = generateListOfMonths(today);

        try {
            // Get all origins
            List<Origins> originsList = origins.findByPriceAirplaneService("skyscanner");

            // Get all destinations
            List<Destinations> destinationsList = destinations.findAll();

            originsList
                    .parallelStream()
                    .forEach(origin -> {
                        destinationsList.forEach(destination -> {
                            months.forEach(month -> {
                                String url = generateURL(origin.getIata(), destination.getIata(), month);

                                logger.info(url);

                                List<Price> prices = api.getPrices(url);
                                prices.forEach((price) -> {
                                    Travels travel = new Travels(origin.getId(), destination.getId(), price.getDepart_date(), price.getValue());
                                    travels.updateTravel(travel);
                                });
                            });
                        });
                    });


        }
        catch (Exception e) {
            logger.error("Error in processTravels", e);
        }
        logger.info("Finished processing");

    }

    public String generateURL(String originIata, String destinationIata, String month) {
        return baseUrl + originIata
                + "/" + destinationIata
                + "/" + month
                + "/?apikey=" + token;
    }

    public List<String> generateListOfMonths(LocalDate today) {
        List<String> months = new ArrayList<>();
        months.add(today.toString());
        for (int i = 1; i <= numberOfMonths - 1; i++) {
            months.add(today.withDayOfMonth(1).plusMonths(i).toString());
        }
        return months;
    }
}
