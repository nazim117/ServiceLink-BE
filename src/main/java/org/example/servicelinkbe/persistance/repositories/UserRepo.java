package org.example.servicelinkbe.persistance.repositories;

import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepo {
    boolean existsByEmail(String email);

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findById(Integer id);

    void deleteById(Integer id);

    UserEntity getUserEntityById(Integer id);

    Collection<UserEntity> getUserEntitiesBy();
}
