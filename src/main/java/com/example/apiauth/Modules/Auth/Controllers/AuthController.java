package com.example.apiauth.Modules.Auth.Controllers;

import com.example.apiauth.Modules.Auth.Models.Dto.LoginRequestDTO;
import com.example.apiauth.Modules.Auth.Models.Dto.RegisterRequestDTO; // New
import com.example.apiauth.Modules.Auth.Services.LoginService;
import com.example.apiauth.Modules.Auth.Services.RegisterService; // New
import com.example.apiauth.Utils.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final RegisterService registerService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO request) {
        String token = loginService.execute(request);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return HttpResponse.send("Login successful", HttpStatus.OK, response);
    }

    @PostMapping("/register-internal")
    public ResponseEntity<Object> registerInternal(@RequestBody RegisterRequestDTO request) {
        registerService.execute(request);
        return HttpResponse.send("User registered successfully", HttpStatus.CREATED, null);
    }
}