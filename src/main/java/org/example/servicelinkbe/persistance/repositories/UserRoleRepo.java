package org.example.servicelinkbe.persistance.repositories;

import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.example.servicelinkbe.persistance.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRoleEntity, Integer> {
    UserRoleEntity save(UserRoleEntity build);

    void deleteUserRoleEntityByUser(UserEntity user);
}
