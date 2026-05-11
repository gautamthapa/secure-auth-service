package com.authservice.infrastructure.repository;

import com.authservice.infrastructure.entity.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {
}
