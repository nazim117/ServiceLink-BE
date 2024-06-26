package org.example.servicelinkbe.business.user_service.interfaces;

import org.example.servicelinkbe.domain.UpdateUserRequest;

import java.nio.file.AccessDeniedException;

public interface UpdateUserUseCase {
    void updateUser(UpdateUserRequest request) throws AccessDeniedException;
}
