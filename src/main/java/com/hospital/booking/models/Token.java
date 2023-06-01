package com.hospital.booking.models;

import com.hospital.booking.enums.TokenType;

import java.time.LocalDateTime;

public class Token {
    private int id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private TokenType type;
    private int accountId;
    public Token() {
    }

    public Token(String token, LocalDateTime createdAt, LocalDateTime expiredAt, TokenType type) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.type = type;
    }

    public Token(int id, String token, LocalDateTime createdAt, LocalDateTime expiredAt, TokenType type) {
        this.id = id;
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.type = type;
    }

    public Token(int id, String token, LocalDateTime createdAt, LocalDateTime expiredAt, TokenType type, int accountId) {
        this.id = id;
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.type = type;
        this.accountId = accountId;
    }

    public Token(String token, LocalDateTime createdAt, LocalDateTime expiredAt, TokenType type, int accountId) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.type = type;
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", createdAt=" + createdAt +
                ", expiredAt=" + expiredAt +
                ", type=" + type +
                ", accountId=" + accountId +
                '}';
    }
}
