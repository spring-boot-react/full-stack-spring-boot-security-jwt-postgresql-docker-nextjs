package com.example.backend.config;

import com.example.backend.payload.i18n.TranslateService;
import com.example.backend.payload.request.HttpRequestService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  @Value("${application.security.jwt.issuer}")
  private String issuer;
  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  private final TranslateService translateService;
  private final HttpRequestService httpRequestService;

  @Override
  public String extractSubject(String jwt) {
    return extractClaim(jwt, Claims::getSubject);
  }

  private String extractIssuer(String jwt) {
    return extractClaim(jwt, Claims::getIssuer);
  }

  private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
    try {
      return claimsResolver.apply(extractAllClaims(jwt));
    } catch (SignatureException e) {
      // TODO register IP when invalid JWT is provided
      this.logErrorMessage("log.jwt.si.invalid.jwt", e.getMessage());
    } catch (MalformedJwtException e) {
      // TODO register IP when malformed JWT is provided
      this.logErrorMessage("log.jwt.si.malformed.jwt", e.getMessage());
    } catch (ExpiredJwtException e) {
      this.logErrorMessage("log.jwt.si.expired.jwt", e.getMessage());
      log.error("Expired JWT: " + e.getMessage());
    } catch (UnsupportedJwtException e) {
      // TODO register IP when unsupported JWT is provided
      this.logErrorMessage("log.jwt.si.unsupported.jwt", e.getMessage());
    }
    return null;
  }

  @Override
  public String generateToken(String email) {
    return generateToken(new HashMap<>(), email);
  }

  private String generateToken(
      Map<String, Object> extraClaims,
      String email
  ) {
    return buildToken(extraClaims, email, jwtExpiration);
  }

  @Override
  public String generateRefreshToken(
      String email
  ) {
    return buildToken(new HashMap<>(), email, refreshExpiration);
  }

  private String buildToken(
          Map<String, Object> extraClaims,
          String email,
          long expiration
  ) {
    return Jwts
            .builder()
            .claims(extraClaims)
            .issuer(issuer)
            .subject(email)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey())
            .compact();
  }

  @Override
  public boolean isTokenValid(String jwt, String email) {
    final String jwtSubject = extractSubject(jwt);
    final String jwtIssuer = extractIssuer(jwt);
    return (jwtSubject.equals(email)) && jwtIssuer.equals(issuer) && !isTokenExpired(jwt);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
            .parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  private SecretKey getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private void logErrorMessage(String code, String error) {
    log.error(translateService.getLogMessage(code),
            httpRequestService.getIp(),
            httpRequestService.getUsername(),
            error
    );
  }
}
