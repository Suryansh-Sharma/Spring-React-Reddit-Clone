package com.suryansh.service;

import com.suryansh.dto.AuthenticationResponse;
import com.suryansh.dto.NotificationEmail;
import com.suryansh.dto.RegisterRequest;
import com.suryansh.entity.User;
import com.suryansh.entity.VerificationToken;
import com.suryansh.exception.SpringRedditException;
import com.suryansh.model.LoginRequest;
import com.suryansh.repository.UserRepository;
import com.suryansh.repository.VerificationTokenRepository;
import com.suryansh.security.JwtProvider;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    // Field Injection for User Repository
    // Field Injection for Password Encoder
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository
            , VerificationTokenRepository verificationTokenRepository
            , MailService mailService, AuthenticationManager authenticationManager,
                           JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public void signUp(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(
                passwordEncoder.encode(
                        registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        // Save User to Repository.
        userRepository.save(user);
        // Send Verification Token to User Email.
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account.",
                user.getEmail(), "Thank you for Sign Up  to Spring Reddit Clone" +
                "please click on the blow link to activate your Account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    //     Account Verification Method.
    @Override
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken =
        verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(()->new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringRedditException("Sorry !! No User Name."));
        user.setEnabled(true);
        userRepository.save(user);
    }

    // Method for login .
    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(token,loginRequest.getUsername());
    }

    @Override
    public User getCurrentUser() {
        Jwt principal =(Jwt)SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(()->new UsernameNotFoundException("User not found of name" + principal.getSubject()));
    }
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
