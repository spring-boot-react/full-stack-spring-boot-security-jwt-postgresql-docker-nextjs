package com.example.backend.auth;

import com.example.backend.config.JwtService;
import com.example.backend.token.TokenService;
import com.example.backend.user.User;
import com.example.backend.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserRepository userRepository;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public AuthenticationResponse register(RegisterRequest request) {
    // TODO Use mapper to model and model to mapper
    var user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .build();
    var savedUser = userRepository.save(user);
    var jwt = jwtService.generateToken(user.getEmail());
    var refreshToken = jwtService.generateRefreshToken(user.getEmail());
    tokenService.saveTokenByUser(jwt, savedUser);
    return AuthenticationResponse.builder()
            .accessToken(jwt)
            .refreshToken(refreshToken)
            .build();
  }

  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwt = jwtService.generateToken(user.getEmail());
    var refreshToken = jwtService.generateRefreshToken(user.getEmail());
    tokenService.revokeAllTokensByUserId(user.getId());
    tokenService.saveTokenByUser(jwt, user);
    // TODO Use mapper to model and model to mapper
    return AuthenticationResponse.builder()
        .accessToken(jwt)
            .refreshToken(refreshToken)
        .build();
  }

  @Override
  public AuthenticationResponse refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return null;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractSubject(refreshToken);
    if (userEmail == null) {
      return null;
    }
    var user = userRepository.findByEmail(userEmail)
            .orElseThrow();
    if (!jwtService.isTokenValid(refreshToken, user.getEmail())) {
      return null;
    }
    var jwt = jwtService.generateToken(user.getEmail());
    tokenService.revokeAllTokensByUserId(user.getId());
    tokenService.saveTokenByUser(jwt, user);
    // TODO Use mapper to model and model to mapper
    return AuthenticationResponse.builder()
            .accessToken(jwt)
            .refreshToken(refreshToken)
            .build();
  }
}