package com.authservice.infrastructure.repository;

import com.authservice.infrastructure.entity.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {

    List<RefreshTokenEntity> findByUserIdAndRevokedFalse(Long userId);

    Optional<RefreshTokenEntity> findByIdAndUserId(Long id, Long userId);

    Optional<RefreshTokenEntity> findByRefreshTokenHashAndRevokedFalse(String refreshToken);
}
