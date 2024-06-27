package org.example.servicelinkbe.business.user_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.domain.UpdateUserRequest;
import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.example.servicelinkbe.persistance.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TestConfig.class})
class UpdateUserUseCaseImplTest {
    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void update_User_ValidRequest_UpdatesUserFields_ReturnsTrue(){
        long userId = 1l;
        UpdateUserRequest request = createUpdateUserRequest(userId);
        UserEntity existingUser = createMockUserEntity(userId);

        when(userRepo.getUserEntityById(userId)).thenReturn(existingUser);
        when(userRepo.save(existingUser)).thenReturn(existingUser);

        assertDoesNotThrow(() -> updateUserUseCase.updateUser(request));

        verify(userRepo).save(existingUser);
        assertEquals(request.getId(), existingUser.getId());
        assertEquals(request.getEmail(), existingUser.getEmail());
        assertEquals(request.getName(), existingUser.getName());
        assertEquals(request.getPassword(), existingUser.getPassword());

    }

    @Test
    void update_User_InvalidUserId_ThrowsException(){
        long invalidUserId = 998l;
        UpdateUserRequest request = createUpdateUserRequest(invalidUserId);

        when(userRepo.getUserEntityById(invalidUserId)).thenReturn(null);

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> updateUserUseCase.updateUser(request));
        assertEquals("User_ID_INVALID", exception.getMessage());
        verify(userRepo, never()).save(any());
    }

    private UserEntity createMockUserEntity(long userId) {
        return UserEntity.builder()
                .id(userId)
                .email("test@example.com")
                .name("John")
                .password("password123")
                .build();
    }

    private UpdateUserRequest createUpdateUserRequest(long userId) {
        return UpdateUserRequest.builder()
                .id(userId)
                .email("test@example.com")
                .name("John")
                .password("password123")
                .build();
    }
}