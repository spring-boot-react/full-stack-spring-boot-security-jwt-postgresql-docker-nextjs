package com.example.backend.config;

import com.example.backend.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

  private final JwtService jwtService;
  private final TokenService tokenService;
  private final UserDetailsService userDetailsService;

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
    final String userEmail = jwtService.extractUsername(jwt);
    if (userEmail != null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
      if (jwtService.isTokenValid(jwt, userDetails) && Boolean.TRUE.equals(tokenService.isTokenValid(jwt))) {
        tokenService.revokeToken(jwt);
      }
    }
  }
}