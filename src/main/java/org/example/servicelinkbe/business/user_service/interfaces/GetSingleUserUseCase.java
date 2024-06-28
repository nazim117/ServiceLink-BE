package org.example.servicelinkbe.business.user_service.interfaces;

import org.example.servicelinkbe.domain.users.User;

import java.nio.file.AccessDeniedException;

public interface GetSingleUserUseCase {
    User get(Long id) throws AccessDeniedException;
}
