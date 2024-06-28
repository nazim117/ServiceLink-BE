package org.example.servicelinkbe.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.user_service.interfaces.*;
import org.example.servicelinkbe.domain.create.CreateUserRequest;
import org.example.servicelinkbe.domain.create.CreateResponse;
import org.example.servicelinkbe.domain.get.GetAllUsersResponse;
import org.example.servicelinkbe.domain.update.UpdateUserRequest;
import org.example.servicelinkbe.domain.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(getUsersUseCase.get());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") final Long id){
        User user = null;
        try {
            user = getUserUseCase.get(id);
            if(user == null){
                return  ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<CreateResponse> createUser(@Valid @RequestBody CreateUserRequest request){
        CreateResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(value = "id") final Long id,
                                           @RequestBody @Valid UpdateUserRequest request){
        request.setId(id);
        try {
            updateUserUseCase.update(request);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") final Long id){
        try{
            deleteUserUseCase.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
