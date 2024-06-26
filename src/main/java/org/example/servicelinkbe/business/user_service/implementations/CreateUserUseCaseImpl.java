package org.example.servicelinkbe.business.user_service.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.user_service.interfaces.CreateUserUseCase;
import org.example.servicelinkbe.domain.CreateUserRequest;
import org.example.servicelinkbe.domain.CreateUserResponse;
import org.example.servicelinkbe.persistance.entity.RoleEnum;
import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.example.servicelinkbe.persistance.entity.UserRoleEntity;
import org.example.servicelinkbe.persistance.repositories.UserRepo;
import org.example.servicelinkbe.persistance.repositories.UserRoleRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepo userRepo;
    private final UserRoleRepo userRoleRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        if(userRepo.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exists");
        }

        UserEntity savedUser = saveNewUser(request);

        RoleEnum role = null;
        if(request.getRole().equals("ADMIN")){
            role = RoleEnum.ADMIN;
        }else if(request.getRole().equals("SERVICE_PROVIDER")){
            role = RoleEnum.SERVICE_PROVIDER;
        }else {
            role = RoleEnum.CLIENT;
        }
        saveUserRole(role, savedUser);

        return CreateUserResponse
                .builder()
                .id(savedUser.getId())
                .build();

    }


    private UserRoleEntity saveUserRole(RoleEnum role, UserEntity user) {
        return userRoleRepo.save(UserRoleEntity.builder().role(role).user(user).build());
    }


    private UserEntity saveNewUser(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserEntity userEntity = UserEntity
                .builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .build();

        return userRepo.save(userEntity);
    }
}
