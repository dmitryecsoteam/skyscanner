package su.ecso.skyscanner.dto;

import org.springframework.data.annotation.Id;

/**
 * Created by Dmitry on 07.10.2018.
 */

public class Origins {

    @Id
    private int id;

    private String iata;
    private String priceAirplaneService;

    public Origins() {
    }

    public Origins(int id, String iata, String priceAirplaneService) {
        this.id = id;
        this.iata = iata;
        this.priceAirplaneService = priceAirplaneService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getPriceAirplaneService() {
        return priceAirplaneService;
    }

    public void setPriceAirplaneService(String priceAirplaneService) {
        this.priceAirplaneService = priceAirplaneService;
    }

    @Override
    public String toString() {
        return "Origins{" +
                "id=" + id +
                ", iata='" + iata + '\'' +
                ", priceAirplaneService='" + priceAirplaneService + '\'' +
                '}';
    }
}
