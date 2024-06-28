package org.example.servicelinkbe.business.user_service.utilities;

import lombok.NoArgsConstructor;
import org.example.servicelinkbe.domain.users.User;
import org.example.servicelinkbe.persistance.entity.UserEntity;

@NoArgsConstructor
public class UserConverter {
    public static User convert(UserEntity user){
        return User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }
}
