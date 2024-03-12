package com.example.backend.auth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    public abstract AuthenticationResponse register(RegisterRequest request);
    public abstract AuthenticationResponse authenticate(AuthenticationRequest request);
    public abstract AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
