package com.trading.auth.controller;

import com.trading.auth.dto.LoginDTO;
import com.trading.auth.dto.UserDTO;
import com.trading.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody UserDTO userDTO) {
        return authService.register(userDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}
