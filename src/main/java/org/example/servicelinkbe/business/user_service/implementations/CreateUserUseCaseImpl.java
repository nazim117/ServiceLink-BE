package org.example.servicelinkbe.business.user_service.implementations;

import org.example.servicelinkbe.business.user_service.interfaces.CreateUserUseCase;
import org.example.servicelinkbe.domain.CreateUserRequest;
import org.example.servicelinkbe.domain.CreateUserResponse;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        return null;
    }
}
