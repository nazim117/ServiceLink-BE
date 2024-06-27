package org.example.servicelinkbe.business.user_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.example.servicelinkbe.persistance.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;

import java.nio.file.AccessDeniedException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class})
class GetSingleUserUseCaseImplTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private GetSingleUserUseCaseImpl getUserUseCase;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    private void mockSecurityContext(String username){
        UserDetails userDetails = new User(username, "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void get_User_UserExists_ReturnsTrue() throws AccessDeniedException {
        long userId = 1l;
        UserEntity mockUser = createMockUser(userId, "test@example.com");
        when(userRepo.getUserEntityById(userId)).thenReturn(mockUser);
        mockSecurityContext("test@example.com");

        org.example.servicelinkbe.domain.users.User user = getUserUseCase.getUser(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId());
    }

    @Test
    void getUser_UserDoesNotExist_ReturnsNull(){
        long nonExistentUserId = 999l;
        when(userRepo.getUserEntityById(nonExistentUserId)).thenReturn(null);
        mockSecurityContext("test@example.com");

        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> getUserUseCase.getUser(nonExistentUserId));

        assertEquals("Invalid user id", exception.getMessage());
    }

    private UserEntity createMockUser(long userId, String email) {
        return UserEntity.builder()
                .id(userId)
                .email(email)
                .name("John")
                .password("password123")
                .build();
    }
    @Test
    void getUser_AccessDenied_ThrowsAccessDeniedException(){
        long userId = 1l;
        UserEntity mockUser = createMockUser(userId, "notAdminTest@example.com");
        when(userRepo.getUserEntityById(userId)).thenReturn(mockUser);
        mockSecurityContext("test@example.com");

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> {
            getUserUseCase.getUser(userId);
        });

        assertEquals("You do not have permission to access this user", exception.getMessage());
    }
}