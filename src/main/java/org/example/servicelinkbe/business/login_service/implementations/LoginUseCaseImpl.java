package org.example.servicelinkbe.business.login_service.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.servicelinkbe.business.login_service.interfaces.LoginUseCase;
import org.example.servicelinkbe.business.login_service.exceptions.InvalidCredentialsException;
import org.example.servicelinkbe.configuration.security.token.AccessTokenEncoder;
import org.example.servicelinkbe.configuration.security.token.impl.AccessTokenImpl;
import org.example.servicelinkbe.domain.login.LoginRequest;
import org.example.servicelinkbe.domain.login.RegisterRequest;
import org.example.servicelinkbe.domain.login.TokenResponse;
import org.example.servicelinkbe.persistance.entity.RoleEnum;
import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.example.servicelinkbe.persistance.entity.UserRoleEntity;
import org.example.servicelinkbe.persistance.repositories.UserRepo;
import org.example.servicelinkbe.persistance.repositories.UserRoleRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;
    private final UserRoleRepo userRoleRepository;

    @Transactional
    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null || !matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = generateAccessToken(user);
        return TokenResponse.builder().accessToken(accessToken).build();
    }

    @Transactional
    @Override
    public TokenResponse register(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exists");
        }

        UserEntity savedUser = saveNewUser(registerRequest);

        RoleEnum role = registerRequest.getRole();
        UserRoleEntity userRoleEntity =  saveUserRole(role, savedUser);

        Set<UserRoleEntity> userRoles = new HashSet<>();
        userRoles.add(userRoleEntity);
        savedUser.setUserRoles(userRoles);


        String accessToken = generateAccessToken(savedUser);
        return TokenResponse.builder().accessToken(accessToken).build();
    }

    private UserRoleEntity saveUserRole(RoleEnum role, UserEntity savedUser) {
        return userRoleRepository.save(UserRoleEntity.builder().role(role).user(savedUser).build());
    }

    private UserEntity saveNewUser(RegisterRequest registerRequest) {
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        UserEntity userEntity = UserEntity
                .builder()
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .password(encodedPassword)
                .build();

        return userRepository.save(userEntity);
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    protected String generateAccessToken(UserEntity user) {
        Long userId = user.getId() != null ? user.getId() : null;
        List<String> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().toString())
                .toList();

        return accessTokenEncoder.encode(
                new AccessTokenImpl(user.getEmail(), userId, roles));
    }
}
