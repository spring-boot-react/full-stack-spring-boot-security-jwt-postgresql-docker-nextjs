package com.example.backend.user;

import com.example.backend.book.Book;
import org.springframework.http.ResponseEntity;
import java.security.Principal;
import java.util.List;

public interface UserService {

    public abstract ResponseEntity<Object> changePassword(ChangePasswordRequest request, Principal currentUser);
    public abstract List<User> findAllUsers();

}
