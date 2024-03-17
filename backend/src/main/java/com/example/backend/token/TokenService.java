package com.example.backend.token;

import com.example.backend.user.User;

public interface TokenService {
    public abstract Boolean isTokenValid(String token);
    public abstract void saveTokenByUser(String token, User user);
    public abstract void revokeAllTokensByUserId(Long id);
    public abstract void revokeTokenByJwt(String token);
}
