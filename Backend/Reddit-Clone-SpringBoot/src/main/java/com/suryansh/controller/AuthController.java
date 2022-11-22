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
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Async
    @PostMapping("/signup")
    public CompletableFuture<ResponseEntity<String>> signUp(@RequestBody RegisterRequest registerRequest) {
        try {
            authService.checkUserIsPresent(registerRequest);
            authService.signUp(registerRequest);
            return CompletableFuture.completedFuture(new ResponseEntity<>("User Registration Successfully"
                    , HttpStatus.CREATED));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(new ResponseEntity<>("UserName is Already Present !!"
                    , HttpStatus.CONFLICT));
        }
    }

    @GetMapping("isUserEnabled/{username}")
    public ResponseEntity<Boolean> isUserActive(@PathVariable String username){
        try {
            authService.isUserEnabled(username);
            return new ResponseEntity<>(true,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("accountVerification/{token}")
    public  ResponseEntity<String>verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully",HttpStatus.OK);
    }

    @Async
    @GetMapping("/resendToken/{username}")
    public CompletableFuture<ResponseEntity<Void>> resendToken(@PathVariable String username){
        authService.resendVerificationToken(username);
        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
    }
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
