package com.example.backend.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Slf4j
@Service
public class JwtService {

  @Value("${application.security.jwt.issuer}")
  private String issuer;
  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  public String extractUsername(String jwt) {
    return extractClaim(jwt, Claims::getSubject);
  }

  public String extractIssuer(String jwt) {
    return extractClaim(jwt, Claims::getIssuer);
  }

  public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
    try {
      return claimsResolver.apply(extractAllClaims(jwt));
    } catch (SignatureException e) {
      // TODO register IP when invalid JWT is provided
      log.error("Invalid signature: " + e.getMessage());
    } catch (MalformedJwtException e) {
      // TODO register IP when malformed JWT is provided
      log.error("Malformed JWT: " + e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT: " + e.getMessage());
    } catch (UnsupportedJwtException e) {
      // TODO register IP when unsupported JWT is provided
      log.error("Unsupported JWT: " + e.getMessage());
    }
    return null;
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails
  ) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  public String generateRefreshToken(
      UserDetails userDetails
  ) {
    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
  }

  private String buildToken(
          Map<String, Object> extraClaims,
          UserDetails userDetails,
          long expiration
  ) {
    return Jwts
            .builder()
            .claims(extraClaims)
            .issuer(issuer)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey())
            .compact();
  }

  public boolean isTokenValid(String jwt, UserDetails userDetails) {
    final String usernameInJwt = extractUsername(jwt);
    final String issuerInJwt = extractIssuer(jwt);
    return (usernameInJwt.equals(userDetails.getUsername())) && issuerInJwt.equals(issuer) && !isTokenExpired(jwt);
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
}
