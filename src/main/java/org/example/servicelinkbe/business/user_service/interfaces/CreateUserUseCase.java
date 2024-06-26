package org.example.servicelinkbe.business.user_service.interfaces;

import org.example.servicelinkbe.domain.CreateUserRequest;
import org.example.servicelinkbe.domain.CreateUserResponse;

public interface CreateUserUseCase {
    CreateUserResponse createUser(CreateUserRequest request);
}
