package com.suryansh.service;

import com.suryansh.dto.AuthenticationResponse;
import com.suryansh.dto.RegisterRequest;
import com.suryansh.entity.User;
import com.suryansh.entity.VerificationToken;
import com.suryansh.exception.SpringRedditException;
import com.suryansh.mail.MailService;
import com.suryansh.model.LoginRequest;
import com.suryansh.model.NotificationEmail;
import com.suryansh.repository.UserRepository;
import com.suryansh.repository.VerificationTokenRepository;
import com.suryansh.security.JwtProvider;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final MailService mailService;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository
            , VerificationTokenRepository verificationTokenRepository,
                           AuthenticationManager authenticationManager,
                           JwtProvider jwtProvider, MailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.mailService = mailService;
    }

    @Transactional
    @Async
    public void signUp(RegisterRequest registerRequest) {
        try {
            // Save User .
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

            NotificationEmail email = new NotificationEmail();
            email.setUserName(user.getUsername());
            email.setSender("Spring-Reddit-Team");
            email.setSubject("Please Verify your Account");
            email.setRecipient(user.getEmail());
            email.setBody("http://localhost:8080/api/auth/accountVerification/"+token);
            mailService.sendAuthTokenMail(email);
        } catch (Exception e) {
            throw new SpringRedditException("Unable to Register User.");
        }
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
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                        .orElseThrow(()->new SpringRedditException("Unable to find Token"));
        fetchUserAndEnable(verificationToken);
        verificationTokenRepository.deleteById(verificationToken.getId());
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
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(()->new SpringRedditException("Sorry no user found"));
        String token = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(token, user.getUsername(),user.isEnabled());
    }

    @Override
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User not found of name" + principal.getSubject()));
    }

    @Override
    public void checkUserIsPresent(RegisterRequest registerRequest) {
        Optional<User> checkUser = userRepository.findByUsername(registerRequest.getUsername());
        if (checkUser.isPresent()) throw new RuntimeException("User already Present");
    }

    @Override
    @Async
    public void resendVerificationToken(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found for token"));
        VerificationToken verificationToken = verificationTokenRepository.findById(user.getUserId())
                .orElseThrow(()->new SpringRedditException("No token found !!"));
        try {
            NotificationEmail email = new NotificationEmail();
            email.setUserName(user.getUsername());
            email.setSender("Spring-Reddit-Team");
            email.setSubject("Please Verify your Account");
            email.setRecipient(user.getEmail());
            email.setBody("http://localhost:8080/api/auth/accountVerification/"+verificationToken.getToken());
            mailService.sendAuthTokenMail(email);
        }catch (MailException m){
            System.out.println("Sorry unable to send Re verification token");
        }
    }

    @Override
    public void isUserEnabled(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new SpringRedditException("No user found"));
        if (!user.isEnabled())throw new SpringRedditException("User is Not Active");
    }
}
