package org.example.servicelinkbe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.servicelinkbe.business.login_service.interfaces.LoginUseCase;
import org.example.servicelinkbe.domain.login.LoginRequest;
import org.example.servicelinkbe.domain.login.RegisterRequest;
import org.example.servicelinkbe.domain.login.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
public class LoginController {
    private final LoginUseCase loginUseCase;

    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        try{
            TokenResponse tokenResponse = loginUseCase.login(loginRequest);
            if(tokenResponse != null){
                return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponse);
            }
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody @Valid RegisterRequest registerRequest){
        try{
            System.out.println("Received register request: " + registerRequest); // Log incoming request
            TokenResponse registerResponse = loginUseCase.register(registerRequest);
            if(registerResponse != null){
                return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
            } else {
                System.out.println("Registration failed"); // Log failure
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            System.err.println("Error during registration: " + e.getMessage()); // Log exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
