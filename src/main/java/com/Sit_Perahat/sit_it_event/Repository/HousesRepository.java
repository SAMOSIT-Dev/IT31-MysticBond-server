package com.Sit_Perahat.sit_it_event.Repository;

import com.Sit_Perahat.sit_it_event.Entity.Houses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HousesRepository extends JpaRepository<Houses, Integer> {

    Houses findHousesByHouseName(String houseName);

}
