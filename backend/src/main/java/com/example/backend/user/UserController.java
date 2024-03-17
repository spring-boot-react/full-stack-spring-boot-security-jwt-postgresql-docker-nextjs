package com.example.backend.user;

import com.example.backend.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(RequestConstants.USERS)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController{

    private final UserService userService;
  private final UserRepository userRepository;

    @PatchMapping
    public ResponseEntity<Object> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal currentUser
    ) {
        userService.changePassword(request, currentUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    @PreAuthorize(value = "hasAuthority('admin:read')")
    public ResponseEntity<List<User>> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/all")
    @PreAuthorize(value = "hasAuthority('admin:read')")
    public String getAllUsers1(){
        return "hello";
    }

}
