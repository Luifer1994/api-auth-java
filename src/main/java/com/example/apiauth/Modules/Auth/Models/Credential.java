package com.example.apiauth.Modules.Auth.Models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID; // Importante

@Data
@Entity
@Table(name = "credentials")
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Asegúrate que sea UUID
    private UUID id; // <--- CAMBIAR DE Long A UUID

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "user_id", nullable = false)
    private UUID userId; // <--- ESTE TAMBIÉN DEBE SER UUID (referencia a ms-users)

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
