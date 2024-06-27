package org.example.servicelinkbe.business.user_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.domain.CreateUserRequest;
import org.example.servicelinkbe.domain.CreateUserResponse;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class})
class CreateUserUseCaseImplTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private UserRoleRepo userRoleRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_createsUser_UserIsFan_Successful(){
        CreateUserRequest request = mock(CreateUserRequest.class);

        when(request.getRole()).thenReturn("FOOTBALL_FAN");

        Set<UserRoleEntity> userRoles = new HashSet<>();
        UserRoleEntity userRoleEntity1 = UserRoleEntity.builder().role(RoleEnum.CLIENT).build();
        UserRoleEntity userRoleEntity2 = UserRoleEntity.builder().role(RoleEnum.ADMIN).build();
        UserRoleEntity userRoleEntity3 = UserRoleEntity.builder().role(RoleEnum.SERVICE_PROVIDER).build();
        userRoles.add(userRoleEntity1);
        userRoles.add(userRoleEntity2);
        userRoles.add(userRoleEntity3);

        UserEntity userEntity = UserEntity.builder()
                .email("test@example.com")
                .name("John")
                .password("password123")
                .userRoles(userRoles)
                .build();

        when(userRepo.existsByEmail(any())).thenReturn(false);
        when(request.getRole()).thenReturn("FOOTBALL_FAN");


        when(userRepo.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userRoleRepo.save(any(UserRoleEntity.class))).thenReturn(UserRoleEntity.builder()
                .role(RoleEnum.CLIENT)
                .user(userEntity)
                .build());
        userEntity.setId(1l);

        CreateUserResponse response = createUserUseCase.createUser(request);

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void create_createUser_Failure_UserExists(){
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@example.com")
                .name("John")
                .password("password123")
                .build();

        when(userRepo.existsByEmail(any())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> createUserUseCase.createUser(request));
    }

    @Test
    void create_createsUser_UserIsAdmin_Successful(){
        CreateUserRequest request = mock(CreateUserRequest.class);

        when(request.getRole()).thenReturn("ADMIN");

        Set<UserRoleEntity> userRoles = new HashSet<>();
        UserRoleEntity userRoleEntity1 = UserRoleEntity.builder().role(RoleEnum.CLIENT).build();
        UserRoleEntity userRoleEntity2 = UserRoleEntity.builder().role(RoleEnum.ADMIN).build();
        UserRoleEntity userRoleEntity3 = UserRoleEntity.builder().role(RoleEnum.SERVICE_PROVIDER).build();
        userRoles.add(userRoleEntity1);
        userRoles.add(userRoleEntity2);
        userRoles.add(userRoleEntity3);

        UserEntity userEntity = UserEntity.builder()
                .email("test@example.com")
                .name("John")
                .password("password123")
                .userRoles(userRoles)
                .build();

        when(userRepo.existsByEmail(any())).thenReturn(false);
        when(request.getRole()).thenReturn("ADMIN");


        when(userRepo.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userRoleRepo.save(any(UserRoleEntity.class))).thenReturn(UserRoleEntity.builder()
                .role(RoleEnum.ADMIN)
                .user(userEntity)
                .build());
        userEntity.setId(1l);

        CreateUserResponse response = createUserUseCase.createUser(request);

        assertNotNull(response);
        assertNotNull(response.getId());

    }

    @Test
    void create_createsUser_UserIsCustomerService_Successful(){
        CreateUserRequest request = mock(CreateUserRequest.class);

        when(request.getRole()).thenReturn("CUSTOMER_SERVICE");

        Set<UserRoleEntity> userRoles = new HashSet<>();
        UserRoleEntity userRoleEntity1 = UserRoleEntity.builder().role(RoleEnum.CLIENT).build();
        UserRoleEntity userRoleEntity2 = UserRoleEntity.builder().role(RoleEnum.ADMIN).build();
        UserRoleEntity userRoleEntity3 = UserRoleEntity.builder().role(RoleEnum.SERVICE_PROVIDER).build();
        userRoles.add(userRoleEntity1);
        userRoles.add(userRoleEntity2);
        userRoles.add(userRoleEntity3);

        UserEntity userEntity = UserEntity.builder()
                .email("test@example.com")
                .name("John")
                .password("password123")
                .userRoles(userRoles)
                .build();

        when(userRepo.existsByEmail(any())).thenReturn(false);
        when(request.getRole()).thenReturn("CUSTOMER_SERVICE");


        when(userRepo.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userRoleRepo.save(any(UserRoleEntity.class))).thenReturn(UserRoleEntity.builder()
                .role(RoleEnum.ADMIN)
                .user(userEntity)
                .build());
        userEntity.setId(1l);

        CreateUserResponse response = createUserUseCase.createUser(request);

        assertNotNull(response);
        assertNotNull(response.getId());

    }
}