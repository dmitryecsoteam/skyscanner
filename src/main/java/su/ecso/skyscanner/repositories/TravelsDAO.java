package su.ecso.skyscanner.repositories;

import su.ecso.skyscanner.dto.Travels;

/**
 * Created by Dmitry on 07.10.2018.
 */
public interface TravelsDAO {
    //Travels findTravel(int origin, int destination, String date);
    void updateTravel(Travels travel);
}
