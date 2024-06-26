package org.example.servicelinkbe.business.user_service.interfaces;

import org.example.servicelinkbe.domain.users.User;

public interface GetSingleUserUseCase {
    User getUser(Integer id);
}
