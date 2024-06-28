package org.example.servicelinkbe.business.user_service.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.user_service.interfaces.GetUsersUseCase;
import org.example.servicelinkbe.business.user_service.utilities.UserConverter;
import org.example.servicelinkbe.domain.get.GetAllUsersResponse;
import org.example.servicelinkbe.domain.users.User;
import org.example.servicelinkbe.persistance.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetUsersUseCaseImpl implements GetUsersUseCase {
    private final UserRepo userRepo;
    @Transactional
    @Override
    public GetAllUsersResponse get() {
        List<User> users = userRepo.getUserEntitiesBy()
                .stream()
                .map(UserConverter::convert)
                .toList();

        return GetAllUsersResponse
                .builder()
                .users(users)
                .build();
    }
}
