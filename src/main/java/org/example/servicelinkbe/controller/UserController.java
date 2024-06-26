package org.example.servicelinkbe.controller;

import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.user_service.interfaces.CreateUserUseCase;
import org.example.servicelinkbe.business.user_service.interfaces.GetSingleUserUseCase;
import org.example.servicelinkbe.business.user_service.interfaces.GetUsersUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/users")
@AllArgsConstructor
public class UserController {
    private final GetUsersUseCase getUsersUseCase;
    private final GetSingleUserUseCase getSingleUserUseCase;
    private final CreateUserUseCase createUserUseCase;
}
