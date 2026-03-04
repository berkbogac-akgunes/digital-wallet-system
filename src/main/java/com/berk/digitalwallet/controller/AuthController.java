package com.berk.digitalwallet.controller;

import com.berk.digitalwallet.dto.AuthResponseLogin;
import com.berk.digitalwallet.dto.AuthResponseRegister;
import com.berk.digitalwallet.dto.LoginRequest;
import com.berk.digitalwallet.dto.RegisterRequest;
import com.berk.digitalwallet.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponseRegister register(
            @Valid @RequestBody RegisterRequest register ) {

        return authService.register(
                register.getEmail(),
                register.getPassword()
        );

    }

    @PostMapping("/login")
    public AuthResponseLogin login(
            @Valid @RequestBody LoginRequest login) {

        return authService.login(
                login.getEmail(),
                login.getPassword()
        );

    }
}
