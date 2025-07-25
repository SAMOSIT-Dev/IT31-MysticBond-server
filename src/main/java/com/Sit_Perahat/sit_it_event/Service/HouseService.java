package com.Sit_Perahat.sit_it_event.Service;

import com.Sit_Perahat.sit_it_event.Entity.Houses;

import java.util.List;
import java.util.Optional;

public interface HouseService {


    List<Houses> getAllHouses();


    Optional<Houses> getHouseById(int id);

}
