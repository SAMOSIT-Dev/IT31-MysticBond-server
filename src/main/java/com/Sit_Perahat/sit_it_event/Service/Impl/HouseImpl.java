package com.Sit_Perahat.sit_it_event.Service.Impl;

import com.Sit_Perahat.sit_it_event.Entity.Houses;
import com.Sit_Perahat.sit_it_event.Repository.HousesRepository;
import com.Sit_Perahat.sit_it_event.Service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HouseImpl implements HouseService {


    @Autowired
    private HousesRepository housesRepository;

    @Override
    public List<Houses> getAllHouses() {
        return housesRepository.findAll();
    }

    @Override
    public Optional<Houses> getHouseById(int id) {
        return housesRepository.findById(id);
    }
}
