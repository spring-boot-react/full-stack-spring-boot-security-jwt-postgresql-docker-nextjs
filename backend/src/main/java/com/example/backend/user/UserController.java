package com.example.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(RequestConstants.USERS)
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;

    @PatchMapping
    public ResponseEntity<Object> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal currentUser
    ) {
        userService.changePassword(request, currentUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable User id) {
        return userService.getUserById(id);
    }

    //TODO Returning Null, must return UserDetails.
    @PatchMapping("/{id}")
    public ResponseEntity<User> changeUserInfo() {
        return userService.changeUserInfo();
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable User id) {
        userService.deleteUserById(id);
    }
}
