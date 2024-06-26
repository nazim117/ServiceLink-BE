package org.example.servicelinkbe.persistance.repositories;

import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.example.servicelinkbe.persistance.entity.UserRoleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo {
    UserRoleEntity save(UserRoleEntity build);

    void deleteUserRoleEntityByUser(UserEntity user);
}
