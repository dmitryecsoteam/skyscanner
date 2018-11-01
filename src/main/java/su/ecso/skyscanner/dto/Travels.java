package su.ecso.skyscanner.dto;

import org.springframework.data.annotation.Id;

/**
 * Created by Dmitry on 07.10.2018.
 */
public class Travels {

    @Id
    private String id;

    private int origin;
    private int destination;
    private String date;
    private int priceAirplane;

    public Travels() {
    }

    public Travels(String id, int origin, int destination, String date, int priceAirplane) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.priceAirplane = priceAirplane;
    }

    public Travels(int origin, int destination, String date, int priceAirplane) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.priceAirplane = priceAirplane;
    }

    public int getPriceAirplane() {
        return priceAirplane;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPriceAirplane(int priceAirplane) {
        this.priceAirplane = priceAirplane;
    }

    @Override
    public String toString() {
        return "Travels{" +
                "id: '" + id + '\'' +
                ", origin: " + origin +
                ", destination: " + destination +
                ", date: '" + date + '\'' +
                ", priceAirplane: " + priceAirplane +
                '}';
    }
}
