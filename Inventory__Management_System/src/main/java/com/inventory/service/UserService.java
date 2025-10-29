package com.inventory.service;

import com.inventory.dao.UserDAO;
import com.inventory.dao.UserDAOImpl;
import com.inventory.model.User;
import com.inventory.util.EmailUtil;
import java.util.Scanner;

public class UserService {

    private final UserDAO userDAO = new UserDAOImpl();
    private final Scanner scanner;

    public UserService(Scanner scanner) {
        this.scanner = scanner;
    }

    // ==================== SIGNUP ====================
    public void signup() {
        System.out.println("\n==== SIGN UP ====");

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter role (admin/user): ");
        String role = scanner.nextLine().trim();

        // Send OTP
        System.out.println("Sending OTP...");
        String otp = EmailUtil.sendOtpEmail(email);

        if (otp == null) {
            System.out.println("❌ Failed to send OTP. Signup aborted.");
            return;
        }

        // Verify OTP
        int attempts = 3;
        boolean verified = false;
        while (attempts > 0) {
            System.out.print("Enter OTP: ");
            String enteredOtp = scanner.nextLine().trim();
            if (EmailUtil.validateOTP(email, enteredOtp)) {
                verified = true;
                break;
            } else {
                attempts--;
                System.out.println("Incorrect OTP. Attempts left: " + attempts);
            }
        }

        if (!verified) {
            System.out.println("❌ Verification failed. Try again.");
            return;
        }

        // Save user
        User user = new User(username, password, role, email);
        boolean success = userDAO.signup(user);

        if (success) System.out.println("✅ Signup successful!");
        else System.out.println("❌ Signup failed. Username may already exist.");
    }

    // ==================== LOGIN ====================
    public User login() {
        System.out.println("\n==== LOGIN ====");

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        User user = userDAO.login(username, password);

        if (user != null) {
            System.out.println("✅ Login successful! Welcome " + user.getUsername());
            return user;
        } else {
            System.out.println("❌ Invalid username or password.");
            return null;
        }
    }
}
