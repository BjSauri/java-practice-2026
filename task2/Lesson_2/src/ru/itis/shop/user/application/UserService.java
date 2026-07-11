package ru.itis.shop.user.application;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String email, String password, String profileDescription) {
        User user = new User(email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get().getPassword().equals(password);
        } else return false;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean updateUserByEmail(String email, String newPassword, String newProfileDescription) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isEmpty()) {
            System.out.println("Пользователь не найден!");
            return false;
        }

        User user = existingUser.get();

        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(newPassword);
        }

        if (newProfileDescription != null && !newProfileDescription.isEmpty()) {
            user.setProfileDescription(newProfileDescription);
        }

        return userRepository.updateByEmail(email, user);
    }
}
