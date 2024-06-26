package org.example.servicelinkbe.business.user_service.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.configuration.SecurityUtils;
import org.example.servicelinkbe.business.user_service.interfaces.UpdateUserUseCase;
import org.example.servicelinkbe.domain.UpdateUserRequest;
import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.example.servicelinkbe.persistance.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepo userRepo;
    @Transactional
    @Override
    public void updateUser(UpdateUserRequest request) throws AccessDeniedException {
        UserEntity userEntity = userRepo.getUserEntityById(request.getId());
        if(userEntity == null){
            throw new NullPointerException("User_ID_INVALID");
        }

        if(!currentUserHasPermission(userEntity)){
            throw new AccessDeniedException("You do not have permission to access this user");
        }

        updateFields(request, userEntity);
    }
    private void updateFields(UpdateUserRequest request, UserEntity user){
        user.setId(request.getId());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        userRepo.save(user);
    }

    private boolean currentUserHasPermission(UserEntity userEntity) {
        String currentUsername = SecurityUtils.getCurrentUsername();
        return currentUsername.equals(userEntity.getEmail());
    }
}
