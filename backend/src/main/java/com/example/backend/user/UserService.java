package com.example.backend.user;

import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Optional;

public interface UserService {

    public abstract ResponseEntity<Object> changePassword(ChangePasswordRequest request, Principal currentUser);
    public abstract ResponseEntity<User> getAllUsers();
    public abstract Optional<User> getUserById(Long id);
    public abstract void updateUser(User user);
    public abstract void deleteUser(Long id);

}
