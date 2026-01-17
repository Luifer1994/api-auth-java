package com.example.apiauth.Modules.Auth.Controllers;

import com.example.apiauth.Modules.Auth.Models.Dto.LoginRequestDTO;
import com.example.apiauth.Modules.Auth.Models.Dto.RegisterRequestDTO; // New
import com.example.apiauth.Modules.Auth.Services.LoginService;
import com.example.apiauth.Modules.Auth.Services.RegisterService; // New
import com.example.apiauth.Utils.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final MessageSource messageSource;

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO request) {
        String token = loginService.execute(request);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return HttpResponse.send(getMessage("auth.login.success"), HttpStatus.OK, response);
    }

    @PostMapping("/register-internal")
    public ResponseEntity<Object> registerInternal(@RequestBody RegisterRequestDTO request) {
        registerService.execute(request);
        return HttpResponse.send(getMessage("auth.register.success"), HttpStatus.CREATED, null);
    }
}