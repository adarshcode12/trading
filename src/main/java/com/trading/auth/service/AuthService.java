package com.trading.auth.service;

import com.trading.auth.dto.LoginDTO;
import com.trading.auth.dto.UserDTO;
import com.trading.auth.model.User;
import com.trading.auth.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String register(UserDTO userDTO) {
        if (userRepository.existsById(userDTO.getEmail())) {
            return "Email already registered!";
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(LoginDTO loginDTO) {
        Optional<User> userOpt = userRepository.findByEmail(loginDTO.getEmail());
        if (userOpt.isEmpty()) {
            return "Invalid email!";
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return "Invalid password!";
        }

        return "Login successful!";
    }
}
