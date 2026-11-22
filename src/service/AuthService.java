package service;

import model.User;
import java.util.ArrayList;

public class AuthService {

    private ArrayList<User> users;
    private User currentUser;

    public AuthService() {
        users = new ArrayList<>();
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public void registerUser(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be empty.");
        }

        if (emailExists(user.getUserEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        users.add(user);
    }

    private boolean emailExists(String email) {
        for (User user : users) {
            if (user.getUserEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public User login(String email, String password) {

        if (email == null || password == null) {
            return null;
        }
        for (User user : users) {

            if (user.getUserEmail().equalsIgnoreCase(email.trim())
                    && user.validatePassword(password)) {

                currentUser = user;
                return user;
            }
        }
        return null;
    }

    public void logout() {
        currentUser = null;
    }
}