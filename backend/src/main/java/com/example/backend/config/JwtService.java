package com.example.backend.config;

public interface JwtService {
    public abstract String extractSubject(String jwt);
    public abstract String generateToken(String email);
    public abstract String generateRefreshToken(String email);
    public abstract boolean isTokenValid(String jwt, String email);
}
