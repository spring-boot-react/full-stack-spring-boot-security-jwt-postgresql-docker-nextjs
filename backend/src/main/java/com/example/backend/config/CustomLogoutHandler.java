package com.example.backend.config;

import com.example.backend.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

  private final JwtService jwtService;
  private final TokenService tokenService;

  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) {
    final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader == null ||!authorizationHeader.startsWith("Bearer ")) {
      return;
    }
    final String jwt = authorizationHeader.substring(7);
    final String userEmail = jwtService.extractSubject(jwt);
    if (userEmail != null && isTokenValid(jwt, userEmail)) {
      tokenService.revokeTokenByJwt(jwt);
    }
  }

  private boolean isTokenValid(String jwt, String userEmail) {
    return jwtService.isTokenValid(jwt, userEmail) && Boolean.TRUE.equals(tokenService.isTokenValid(jwt));
  }
}