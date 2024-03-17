package com.example.backend.user;

import com.example.backend.payload.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

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

    @Override
    public List<User> getAllUsers() {
        return  userRepository.findAll();
    }

    @Override
    public ResponseEntity<User> getUserById(User id) {
        return ResponseEntity.ok().body(this.findById(id));
    }

    @Override
    public void deleteUserById(User id) {

    }
    @Override
    public ResponseEntity<User> changeUserInfo() {
        return null;
    }

    private User findById(User id) {
        return userRepository.findById(id.getId())
                .orElseThrow(() -> new ResourceNotFoundException("user.si.not.found",  new String[]{String.valueOf(id)}));
    }
}
