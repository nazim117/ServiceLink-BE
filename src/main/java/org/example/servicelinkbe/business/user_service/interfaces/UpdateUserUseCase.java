package org.example.servicelinkbe.business.user_service.interfaces;

import org.example.servicelinkbe.domain.update.UpdateUserRequest;

import java.nio.file.AccessDeniedException;

public interface UpdateUserUseCase {
    void update(UpdateUserRequest request) throws AccessDeniedException;
}
