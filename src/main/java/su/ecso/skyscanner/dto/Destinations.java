package su.ecso.skyscanner.dto;

import org.springframework.data.annotation.Id;

/**
 * Created by Dmitry on 07.10.2018.
 */
public class Destinations {

    @Id
    private int id;

    private String iata;

    public Destinations() {
    }

    public Destinations(int id, String iata) {
        this.id = id;
        this.iata = iata;
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

    @Override
    public String toString() {
        return "Destinations{" +
                "id: " + id +
                ", iata: '" + iata + '\'' +
                '}';
    }
}
