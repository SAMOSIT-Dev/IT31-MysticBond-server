package com.Sit_Perahat.sit_it_event.Service;

import com.Sit_Perahat.sit_it_event.dto.LoginResponse;

import java.io.IOException;

public interface AuthService {

    LoginResponse Login(String username, String password) throws IOException, InterruptedException;

}
