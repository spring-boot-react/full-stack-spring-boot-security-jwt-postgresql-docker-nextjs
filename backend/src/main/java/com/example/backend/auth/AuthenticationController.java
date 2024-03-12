package com.example.backend.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<Object> register(
      @RequestBody RegisterRequest request
  ) {
    return new ResponseEntity<>(authenticationService.register(request), HttpStatus.OK);
  }
  @PostMapping("/authenticate")
  public ResponseEntity<Object> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<Object> refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    return new ResponseEntity<>(authenticationService.refreshToken(request, response), HttpStatus.OK);
  }


}
