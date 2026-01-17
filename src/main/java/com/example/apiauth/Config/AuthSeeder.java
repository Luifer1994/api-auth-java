package com.example.apiauth.Config;

import com.example.apiauth.Modules.Auth.Models.Credential;
import com.example.apiauth.Modules.Auth.Repositories.CredentialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class AuthSeeder implements CommandLineRunner {

    private final CredentialRepository credentialRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Mismo UUID que pusimos en api-user
    private static final UUID SUPER_ADMIN_ID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");

    public AuthSeeder(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "almendralesluifer@gmail.com";

        if (credentialRepository.findByEmail(adminEmail).isEmpty()) {
            Credential cred = new Credential();
            cred.setEmail(adminEmail);
            // Password: "password123" (Hasheado)
            cred.setPasswordHash(passwordEncoder.encode("1004280446"));
            cred.setUserId(SUPER_ADMIN_ID); // Conectamos con el usuario

            credentialRepository.save(cred);
            System.out.println("✅ SUPER ADMIN CREDENTIALS CREADAS");
        }
    }
}