package org.example.servicelinkbe.business.user_service.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.SecurityUtils;
import org.example.servicelinkbe.business.user_service.interfaces.GetSingleUserUseCase;
import org.example.servicelinkbe.business.user_service.utilities.UserConverter;
import org.example.servicelinkbe.domain.users.User;
import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.example.servicelinkbe.persistance.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@AllArgsConstructor
public class GetSingleUserUseCaseImpl implements GetSingleUserUseCase {
    private final UserRepo userRepo;
    @Transactional
    @Override
    public User getUser(Integer id) throws AccessDeniedException {
        UserEntity userEntity =  userRepo.getUserEntityById(id);
        if(userEntity == null){
            throw new NullPointerException("Invalid user id");
        }

        if(!currentUserHasPermission(userEntity)){
            throw new AccessDeniedException("You do not have permission to access this user");
        }

        return UserConverter.convert(userEntity);
    }

    private boolean currentUserHasPermission(UserEntity userEntity) {
        String currentUsername = SecurityUtils.getCurrentUsername();
        return currentUsername.equals(userEntity.getEmail());
    }
}

