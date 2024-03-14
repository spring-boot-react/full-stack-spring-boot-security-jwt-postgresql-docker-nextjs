package com.example.backend.token;

import com.example.backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public Boolean isTokenValid(String token) {
        return tokenRepository.findByToken(token)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);
    }

    @Override
    public void saveTokenByUser(String token, User user) {
        var newToken = Token.builder()
                .user(user)
                .token(token)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(newToken);
    }

    @Override
    public void revokeAllTokensByUserId(int id) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(id);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void revokeTokenByJwt(String token) {
        var currentToken = tokenRepository.findByToken(token)
                .orElse(null);
        if (currentToken != null) {
            currentToken.setExpired(true);
            currentToken.setRevoked(true);
            tokenRepository.save(currentToken);
        }
    }
}