package com.Sit_Perahat.sit_it_event.Service.Impl;

import com.Sit_Perahat.sit_it_event.Entity.Houses;
import com.Sit_Perahat.sit_it_event.Entity.Users;
import com.Sit_Perahat.sit_it_event.Repository.UsersRepository;
import com.Sit_Perahat.sit_it_event.Service.AuthService;
import com.Sit_Perahat.sit_it_event.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsersImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;


    @Override
    public Users findUser(String studentId) {
        return usersRepository.findUsersByStudentId(studentId).orElseThrow(RuntimeException::new);
    }

    @Override
    public Users UpdateUser(Houses houses,Users users) {
        users.setHouse(houses);
        users.setIsAnswered(true);
        usersRepository.save(users);
        return users;
    }
}
