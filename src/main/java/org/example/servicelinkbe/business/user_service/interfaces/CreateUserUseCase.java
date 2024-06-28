package org.example.servicelinkbe.business.user_service.interfaces;

import org.example.servicelinkbe.domain.create.CreateUserRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;

public interface CreateUserUseCase {
    CreateResponse createUser(CreateUserRequest request);
}
