package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.sd.a2.dto.UserLoginDto;
import ro.sd.a2.entity.ResetPassword;
import ro.sd.a2.entity.Role;
import ro.sd.a2.entity.User;
import ro.sd.a2.repository.UserRepository;
import ro.sd.a2.utils.Messages;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for user entity.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Registers a new user in the database. It attributes the default role of user. It checks if the there is another user
     * with the same email or username. If not, it inserts the user.
     * @param user The user to be inserted
     * @return The newly inserted user.
     */
    public String registerNewUserAccount(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = new Role();
        role.setId(1);
        user.setRole(role);
        User userEmail = userRepository.findByEmail(user.getEmail());
        if(userEmail!=null) return Messages.emailAlreadyExistMessage;
        User userUsername = userRepository.findByUsername(user.getUsername());
        if(userUsername!=null) return Messages.usernameAlreadyExistMessage;

        userRepository.save(user);
        return "OK";
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Find a user by email or username.
     * @param username The email or username of the user we want to find
     * @return null if the user doesn't exist, or the user.
     */
    public User findByEmailOrUsername(String username) {
        User user;
        user = userRepository.findByEmail(username);
        if(user==null) user = userRepository.findByUsername(username);
        return user;
    }

    /**
     * Change the password of the user.
     * @param user The user whose password we want to change.
     * @param password The new unencrypted password.
     */
    public void changePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    /**
     * Get all the users with the role USER
     * @return a list of users.
     */
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().filter(e->e.getRole().getName().equals("USER")).collect(Collectors.toList());
    }
}
