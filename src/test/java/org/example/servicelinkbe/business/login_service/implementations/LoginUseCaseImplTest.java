package org.example.servicelinkbe.business.login_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.business.login_service.exceptions.InvalidCredentialsException;
import org.example.servicelinkbe.configuration.security.token.AccessTokenEncoder;
import org.example.servicelinkbe.domain.login.LoginRequest;
import org.example.servicelinkbe.domain.login.RegisterRequest;
import org.example.servicelinkbe.domain.login.TokenResponse;
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
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class})
class LoginUseCaseImplTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccessTokenEncoder accessTokenEncoder;
    @Mock
    private UserRoleRepo userRoleRepo;
    @InjectMocks
    private LoginUseCaseImpl loginUseCase;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_WithValidCredentials_ReturnsAccessToken(){
        String email = "test@test.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        String accessToken = "accessToken";

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .id(1L)
                .role(RoleEnum.CLIENT)
                .user(userEntity)
                .build();

        Set<UserRoleEntity> userRoleEntities = new HashSet<>();
        userRoleEntities.add(userRoleEntity);
        userEntity.setUserRoles(userRoleEntities);

        LoginRequest request = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        when(userRepo.findByEmail(email)).thenReturn(userEntity);
        when(passwordEncoder.matches(password,encodedPassword)).thenReturn(true);
        when(accessTokenEncoder.encode(any())).thenReturn(accessToken);

        TokenResponse response = loginUseCase.login(request);

        assertNotNull(response);
        assertEquals(accessToken, response.getAccessToken());
    }

    @Test
    void login_WithInvalidCredentials_ThrowsInvalidCredentialsException(){
        //Arrange
        String email = "test@test.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .id(1L)
                .role(RoleEnum.CLIENT)
                .user(userEntity)
                .build();

        Set<UserRoleEntity> userRoleEntities = new HashSet<>();
        userRoleEntities.add(userRoleEntity);
        userEntity.setUserRoles(userRoleEntities);

        LoginRequest request = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        when(userRepo.findByEmail(email)).thenReturn(userEntity);
        when(passwordEncoder.matches(password,encodedPassword)).thenReturn(false);

        //Act
        //Assert
        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(request));
    }

    @Test
    void login_WithNullUser_ThrowsInvalidCredentialsException(){
        String email = "test@email.com";
        String password = "Password_123";

        LoginRequest request = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        when(userRepo.findByEmail(email)).thenReturn(null);

        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(request));
    }

    @Test
    void register_WithValidRequest_ReturnsRegisterResponse(){
        String email = "test@test.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        String accessToken = "accessToken";

        RegisterRequest request = RegisterRequest.builder()
                .email(email)
                .password(password)
                .build();

        when(userRepo.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(accessTokenEncoder.encode(any())).thenReturn(accessToken);

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .name("John")
                .password(encodedPassword)
                .build();

        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .id(1L)
                .role(RoleEnum.CLIENT)
                .user(userEntity)
                .build();

        Set<UserRoleEntity> userRoleEntities = new HashSet<>();
        userRoleEntities.add(userRoleEntity);
        userEntity.setUserRoles(userRoleEntities);

        when(userRepo.save(any())).thenReturn(userEntity);
        when(userRoleRepo.save(any())).thenReturn(userRoleEntity);

        TokenResponse response = loginUseCase.register(request);

        assertNotNull(response);
        assertEquals(accessToken, response.getAccessToken());
    }

    @Test
    void register_WithExistingEmail_ThrowsResponseStatusException() {
        String email = "test@test.com";
        String password = "password";

        RegisterRequest request = RegisterRequest.builder()
                .email(email)
                .password(password)
                .build();

        when(userRepo.existsByEmail(email)).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> loginUseCase.register(request));
    }

    @Test
    void generateAccessToken_WithNullUserId_ReturnsAccessToken(){
        String email = "test@test.com";
        String password = "password";
        String accessToken = "accessToken";

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .password(password)
                .build();

        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .id(1L)
                .role(RoleEnum.CLIENT)
                .user(userEntity)
                .build();

        Set<UserRoleEntity> userRoleEntities = new HashSet<>();
        userRoleEntities.add(userRoleEntity);
        userEntity.setUserRoles(userRoleEntities);

        when(accessTokenEncoder.encode(any())).thenReturn(accessToken);

        String generatedAccessToken = loginUseCase.generateAccessToken(userEntity);

        assertEquals(accessToken, generatedAccessToken);
    }
}