package com.gauri.noteVault.repository;

import com.gauri.noteVault.entity.RefreshToken;
import com.gauri.noteVault.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {


    Optional<RefreshToken> findByTokenHash(String tokenHash);
    List<RefreshToken> findBySession(Session session);
    void deleteBySession(Session session);

    // Add this method
    void deleteBySessionAndRevokedTrue(Session session);



}


