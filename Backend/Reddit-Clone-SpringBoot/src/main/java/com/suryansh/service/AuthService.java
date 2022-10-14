package com.suryansh.service;

import com.suryansh.dto.AuthenticationResponse;
import com.suryansh.dto.RegisterRequest;
import com.suryansh.entity.User;
import com.suryansh.model.LoginRequest;

public interface AuthService {
    void signUp(RegisterRequest registerRequest);

    void verifyAccount(String token);

    AuthenticationResponse login(LoginRequest loginRequest);

    User getCurrentUser();
}
