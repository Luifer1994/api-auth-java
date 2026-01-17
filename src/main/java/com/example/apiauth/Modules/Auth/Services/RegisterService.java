package com.example.apiauth.Modules.Auth.Services;

import com.example.apiauth.Modules.Auth.Models.Credential;
import com.example.apiauth.Modules.Auth.Models.Dto.RegisterRequestDTO;
import com.example.apiauth.Modules.Auth.Repositories.CredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final CredentialRepository credentialRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void execute(RegisterRequestDTO request) {
        Credential credential = new Credential();
        credential.setUserId(request.getUserId());
        credential.setEmail(request.getEmail());
        credential.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        credentialRepository.save(credential);
    }
}
