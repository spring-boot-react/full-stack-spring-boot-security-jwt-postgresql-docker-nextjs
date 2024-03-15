package com.example.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *Authors: J.Spaan & J. Hamwijk
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /** TODO Remove all comments and add javadoc
     * @param request ChangePasswordRequest,
     * @param connectedUser getPrincipal, find user by connectedUser
     * @return ResponseEntity.accepted().body(HttpStatus.ACCEPTED);
     */
    public ResponseEntity<Object> changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
        return ResponseEntity.accepted().body(HttpStatus.ACCEPTED);
    }

    /** TODO Write javadoc instead of all comments in the code
     * @return
     */
    // Get all users
    public ResponseEntity<User> getAllUsers() { // Generate function
        List<User> users = new ArrayList<>();
        userRepository.findAll() // Call JPA function via the Repository
                .forEach(users::add);
        return (ResponseEntity<User>) users;  //return list of users
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) { // Generate function
        return userRepository.findById(id); // Call JPA function via the Repository
    }

    // Update user
    public void updateUser(User user) { // Generate function
        userRepository.save(user); // Call JPA function via the Repository
    }

    // Delete user
    public void deleteUser(Long id) { // Generate function
        userRepository.deleteById(id); // Call JPA function via the Repository
    }

    // Find users by Email
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    //    // Back-end updatePassword in progress
    //    public void updatePassword(String updatedPassword) {
    //        userRepository.findByEmail(updatedPassword);
    //    }
}
