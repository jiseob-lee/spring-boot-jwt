package rg.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rg.jwt.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	   Optional<RefreshToken> findByRefreshToken(String refreshToken);
	   boolean existsByKeyEmail(String userEmail);
	   void deleteByKeyEmail(String userEmail);
	}
