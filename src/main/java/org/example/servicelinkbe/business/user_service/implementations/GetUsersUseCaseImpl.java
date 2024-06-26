package org.example.servicelinkbe.business.user_service.implementations;

import org.example.servicelinkbe.business.user_service.interfaces.GetUsersUseCase;
import org.example.servicelinkbe.domain.GetAllUsersResponse;
import org.springframework.stereotype.Service;

@Service
public class GetUsersUseCaseImpl implements GetUsersUseCase {
    @Override
    public GetAllUsersResponse getUsers() {
        return null;
    }
}
