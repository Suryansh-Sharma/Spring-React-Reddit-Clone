package com.suryansh.controller;

import com.suryansh.dto.AuthenticationResponse;
import com.suryansh.dto.RegisterRequest;
import com.suryansh.model.LoginRequest;
import com.suryansh.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Async
    @PostMapping("/signup")
    public CompletableFuture<ResponseEntity<String>> signUp(@RequestBody RegisterRequest registerRequest){
        authService.signUp(registerRequest);
        return CompletableFuture.completedFuture(new ResponseEntity<>("User Registration Successfully"
                , HttpStatus.OK));
    }

    @GetMapping("accountVerification/{token}")
    public  ResponseEntity<String>verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully",HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
