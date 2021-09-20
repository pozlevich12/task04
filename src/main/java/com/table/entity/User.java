package com.table.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String providerKey;
    private String name;
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;
    private LocalDateTime firstVisit;
    private LocalDateTime lastVisit;
    private boolean blocked;

    public User(String providerKey, String name, AuthProvider authProvider, LocalDateTime firstVisit,
            LocalDateTime lastVisit, boolean blocked) {
        this.providerKey = providerKey;
        this.name = name;
        this.authProvider = authProvider;
        this.firstVisit = firstVisit;
        this.lastVisit = lastVisit;
        this.blocked = blocked;
    }

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setProviderKey(String providerKey) {
        this.providerKey = providerKey;
    }

    public String getProviderKey() {
        return providerKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public LocalDateTime getFirstVisit() {
        return firstVisit;
    }

    public void setFirstVisit(LocalDateTime firstVisit) {
        this.firstVisit = firstVisit;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}