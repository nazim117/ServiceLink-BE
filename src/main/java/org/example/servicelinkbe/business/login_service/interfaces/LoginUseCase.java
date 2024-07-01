package org.example.servicelinkbe.business.login_service.interfaces;

import org.example.servicelinkbe.domain.login.LoginRequest;
import org.example.servicelinkbe.domain.login.RegisterRequest;
import org.example.servicelinkbe.domain.login.TokenResponse;

public interface LoginUseCase {
    TokenResponse login(LoginRequest loginRequest);

    TokenResponse register(RegisterRequest registerRequest);
}
