package org.example.servicelinkbe.business.user_service.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.user_service.interfaces.DeleteUserUseCase;
import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.example.servicelinkbe.persistance.repositories.UserRepo;
import org.example.servicelinkbe.persistance.repositories.UserRoleRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserRepo userRepo;
    private final UserRoleRepo userRoleRepo;
    @Transactional
    @Override
    public void deleteUser(Long id) {
        UserEntity user = userRepo.findById(id).orElse(null);
        if(user != null) {
            userRepo.deleteById(id);
            userRoleRepo.deleteUserRoleEntityByUser(user);

        }
    }
}
