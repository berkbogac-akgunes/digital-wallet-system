package com.berk.digitalwallet.service;

import com.berk.digitalwallet.dto.AuthResponseLogin;
import com.berk.digitalwallet.dto.AuthResponseRegister;
import com.berk.digitalwallet.entity.User;
import com.berk.digitalwallet.entity.Wallet;
import com.berk.digitalwallet.exception.EmailAlreadyExistsException;
import com.berk.digitalwallet.exception.InvalidCredentialsException;
import com.berk.digitalwallet.repository.UserRepository;
import com.berk.digitalwallet.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseRegister register(String email, String rawPassword) {

        if(userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(email, hashedPassword);

        Wallet wallet = new Wallet();
        user.assignWallet(wallet);

        userRepository.save(user);

        return new AuthResponseRegister(user.getEmail());

    }

    public AuthResponseLogin login(String email, String rawPassword) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        if(!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponseLogin(token);

    }


}
