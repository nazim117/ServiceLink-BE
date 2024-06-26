package org.example.servicelinkbe.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.user_service.interfaces.*;
import org.example.servicelinkbe.domain.CreateUserRequest;
import org.example.servicelinkbe.domain.CreateUserResponse;
import org.example.servicelinkbe.domain.GetAllUsersResponse;
import org.example.servicelinkbe.domain.UpdateUserRequest;
import org.example.servicelinkbe.domain.users.User;
import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final GetUsersUseCase getUsersUseCase;
    private final GetSingleUserUseCase getUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @GetMapping
    @RolesAllowed({"ADMIN", "CUSTOMER_SERVICE"})
    public ResponseEntity<GetAllUsersResponse> getUsers(){
        return ResponseEntity.ok(getUsersUseCase.getUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") final Integer id){
        User user = null;
        try {
            user = getUserUseCase.getUser(id);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
        if(user == null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request){
        CreateUserResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(value = "id") final Integer id,
                                           @RequestBody @Valid UpdateUserRequest request){
        request.setId(id);
        try {
            updateUserUseCase.updateUser(request);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") final Integer id){
        deleteUserUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
