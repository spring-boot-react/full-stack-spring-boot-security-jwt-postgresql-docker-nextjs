package com.example.backend.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequestMapping(RequestConstants.API + RequestConstants.USERS)
public interface UserApi {

    @PatchMapping
    ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest request, Principal currentUser);

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    List<User> getAllUsers();

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    Optional<User> getUserById(@PathVariable Long id);

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    void updateUser(@Valid @RequestBody User user);

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteUser(@PathVariable Long id);
}
