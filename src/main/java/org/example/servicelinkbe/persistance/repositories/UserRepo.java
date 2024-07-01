package org.example.servicelinkbe.persistance.repositories;

import org.example.servicelinkbe.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    boolean existsByEmail(String email);

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findById(Long id);

    void deleteById(Long id);

    UserEntity getUserEntityById(Long id);

    Collection<UserEntity> getUserEntitiesBy();

    UserEntity findByEmail(String email);
}
