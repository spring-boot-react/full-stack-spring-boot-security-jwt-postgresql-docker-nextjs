package com.example.backend.payload.request;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public record HttpRequestServiceImpl(HttpServletRequest httpServletRequest) implements HttpRequestService {

    @Override
    public String getUsername() {

        if (httpServletRequest.getUserPrincipal() != null) {
            return httpServletRequest.getUserPrincipal().getName();
        }
        return null;
    }

    @Override
    public String getIp() {
        final String xfHeader = httpServletRequest.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return httpServletRequest.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}