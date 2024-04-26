package com.team3.blogteam3.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;


public interface SessionRepository extends JpaRepository<Session, String> {
    Optional<Session> findByIdAndExpiresAtAfter(String sessionId, Instant now);

    void deleteById(String sessionId);
}
