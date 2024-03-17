package com.example.backend.user;

import org.springframework.http.ResponseEntity;
import java.security.Principal;
import java.util.List;

public interface UserService {

    public abstract ResponseEntity<Object> changePassword(ChangePasswordRequest request, Principal currentUser);
    public abstract List<User> getAllUsers();
    public abstract ResponseEntity<User> getUserById(User id);
    public abstract ResponseEntity<User> changeUserInfo();
    public abstract void deleteUserById(User id);

}
