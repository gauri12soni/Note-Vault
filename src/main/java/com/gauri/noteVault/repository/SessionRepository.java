package com.gauri.noteVault.repository;

import com.gauri.noteVault.entity.Session;
import com.gauri.noteVault.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {

    // Get user's session (only one exists)
    Optional<Session> findByUser(User user);

    // Get session by ID (no revoked check needed)
    Optional<Session> findBySessionId(String sessionId);

    // Cleanup expired sessions
    List<Session> findByExpiresAtBefore(Instant now);

    // Delete by user
    void deleteByUser(User user);




}
