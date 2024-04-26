package com.team3.blogteam3.session;

import com.team3.blogteam3.user.User;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="session")
public class Session {
    @Id
    private String id = UUID.randomUUID().toString();
    @ManyToOne
    private User user;
    @Column(name = "expires_at")
    private Instant expiresAt;

    public Session() {
    }

    public Session(User user, Instant instant) {
        this.user = user;
        this.expiresAt = instant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
