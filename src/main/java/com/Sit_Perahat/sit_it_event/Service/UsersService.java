package com.Sit_Perahat.sit_it_event.Service;

import com.Sit_Perahat.sit_it_event.Entity.Houses;
import com.Sit_Perahat.sit_it_event.Entity.Users;

public interface UsersService {

    Users findUser(String studentId);

    Users UpdateUser(Houses houses,Users users);
}
