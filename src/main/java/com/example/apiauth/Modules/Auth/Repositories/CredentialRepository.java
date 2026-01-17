package com.example.apiauth.Modules.Auth.Repositories;

import com.example.apiauth.Modules.Auth.Models.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CredentialRepository extends JpaRepository<Credential, UUID> {
    Optional<Credential> findByEmail(String email);
}
