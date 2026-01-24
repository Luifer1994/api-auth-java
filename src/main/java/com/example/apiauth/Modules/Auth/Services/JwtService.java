package com.example.apiauth.Modules.Auth.Services;

import com.example.apiauth.Modules.Auth.Models.Credential;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.nio.charset.StandardCharsets;

@Service
public class JwtService {
    // En producción, esto debe venir de variables de entorno
    private final String SECRET = "ESTA_ES_UNA_LLAVE_MUY_SECRETA_Y_LARGA_PARA_SEGURIDAD_123456";
    private final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String createToken(Credential credential) {
        return Jwts.builder()
                .subject(credential.getUserId().toString())
                .claim("email", credential.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(KEY)
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public io.jsonwebtoken.Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}