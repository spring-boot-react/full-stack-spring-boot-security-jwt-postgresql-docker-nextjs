package com.example.backend.token;

import com.example.backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public Boolean isTokenValid(String token) {
        return tokenRepository.findByToken(token)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);
    }

    public void saveUserToken(User user, String token) {
        var newToken = Token.builder()
                .user(user)
                .token(token)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(newToken);
    }

    public void revokeAllTokensByUser(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void revokeToken(String token) {
        var currentToken = tokenRepository.findByToken(token)
                .orElse(null);
        if (currentToken != null) {
            currentToken.setExpired(true);
            currentToken.setRevoked(true);
            tokenRepository.save(currentToken);
        }
    }
}