package org.example.servicelinkbe.business.user_service.implementations;

import org.example.servicelinkbe.TestConfig;
import org.example.servicelinkbe.business.user_service.utilities.UserConverter;
import org.example.servicelinkbe.domain.get.GetAllUsersResponse;
import org.example.servicelinkbe.domain.users.User;
import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.example.servicelinkbe.persistance.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestConfig.class})
class GetUsersUseCaseImplTest {
    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private GetUsersUseCaseImpl getUsersUseCase;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void get_Users_NoUsersExist_ReturnsEmptyResponse(){
        when(userRepo.getUserEntitiesBy()).thenReturn(new ArrayList<>());

        GetAllUsersResponse response = getUsersUseCase.get();

        assertEquals(0, response.getUsers().size());
    }

    @Test
    void get_Users_UsersExist_ReturnsUsersResponse(){
        List<UserEntity> mockUsers = createMockUsers();
        when(userRepo.getUserEntitiesBy()).thenReturn(mockUsers);

        List<User> users = mockUsers.stream()
                .map(UserConverter::convert)
                .toList();

        GetAllUsersResponse response = getUsersUseCase.get();

        assertEquals(users.size(), response.getUsers().size());
        assertEquals(users, response.getUsers());
    }

    private List<UserEntity> createMockUsers() {
        List<UserEntity> mockUsers = new ArrayList<>();
        mockUsers.add(UserEntity.builder()
                .id(1l)
                .email("test@example.com")
                .name("John")
                .password("password123")
                .build());

        mockUsers.add(UserEntity.builder()
                .id(2l)
                .email("try@example.com")
                .name("Michelle")
                .password("password456")
                .build());

        return mockUsers;
    }
}