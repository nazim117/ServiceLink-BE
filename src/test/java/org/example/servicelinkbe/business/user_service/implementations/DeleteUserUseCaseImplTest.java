package org.example.servicelinkbe.business.user_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.persistance.entity.RoleEnum;
import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.example.servicelinkbe.persistance.entity.UserRoleEntity;
import org.example.servicelinkbe.persistance.repositories.UserRepo;
import org.example.servicelinkbe.persistance.repositories.UserRoleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TestConfig.class})
class DeleteUserUseCaseImplTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private UserRoleRepo userRoleRepo;
    @InjectMocks
    private DeleteUserUseCaseImpl deleteUserUseCase;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void delete_User_ValidId_DeletesUser(){
        long userId = 1l;



        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .build();

        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .id(1l)
                .role(RoleEnum.CLIENT)
                .user(userEntity)
                .build();

        Set<UserRoleEntity> userRoleEntities = new HashSet<>();
        userRoleEntities.add(userRoleEntity);
        userEntity.setUserRoles(userRoleEntities);

        when(userRepo.findById(userId)).thenReturn(Optional.of(userEntity));

        assertDoesNotThrow(() -> deleteUserUseCase.deleteUser(userId));

        verify(userRepo).deleteById(userId);
        verify(userRoleRepo).deleteUserRoleEntityByUser(userEntity);
    }

    @Test
    void delete_User_InvalidId_ThrowsException(){
        long invalidUserId = -1l;

        when(userRepo.findById(invalidUserId)).thenReturn(Optional.empty());

        deleteUserUseCase.deleteUser(invalidUserId);

        verify(userRepo, never()).deleteById(anyInt());
        verify(userRoleRepo, never()).deleteUserRoleEntityByUser(any(UserEntity.class));
    }
}