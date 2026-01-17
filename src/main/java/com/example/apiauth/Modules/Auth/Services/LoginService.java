package com.example.apiauth.Modules.Auth.Services;

import com.example.apiauth.Modules.Auth.Models.Dto.LoginRequestDTO;
import com.example.apiauth.Modules.Auth.Repositories.CredentialRepository;
import com.example.apiauth.Exceptions.ResourceNotFoundException; // Asegúrate que exista
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final CredentialRepository credentialRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String execute(LoginRequestDTO request) {
        var credential = credentialRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Credentials invalid"));

        if (!passwordEncoder.matches(request.getPassword(), credential.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtService.createToken(credential);
    }
}