package org.example.servicelinkbe.business.user_service.implementations;

import org.example.servicelinkbe.business.user_service.interfaces.GetSingleUserUseCase;
import org.example.servicelinkbe.domain.users.User;
import org.springframework.stereotype.Service;

@Service
public class GetSingleUserUseCaseImpl implements GetSingleUserUseCase {
    @Override
    public User getUser(Integer id) {
        return null;
    }
}
