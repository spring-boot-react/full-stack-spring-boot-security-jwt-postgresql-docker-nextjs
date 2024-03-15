package com.example.backend.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest request, Principal currentUser) {
        return userService.changePassword(request, currentUser);
    }

    //TODO getAllUsers Fix return statement
    @Override
    public List<User> getAllUsers() {
        return (List<User>) userService.getAllUsers();
    }

    @Override
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Override
    public void updateUser(@Valid @RequestBody User user) {
        userService.updateUser(user);
    }

    @Override
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
