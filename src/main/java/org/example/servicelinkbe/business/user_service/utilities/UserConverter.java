package org.example.servicelinkbe.business.user_service.utilities;

import org.example.servicelinkbe.domain.users.User;
import org.example.servicelinkbe.persistance.entity.UserEntity;

public class UserConverter {
    private UserConverter(){

    }
    public static User convert(UserEntity user){
        return User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }
}
