package com.example.backend.user;

import java.security.Principal;

public interface UserService {
    public abstract void changePassword(ChangePasswordRequest request, Principal currentUser);
}
