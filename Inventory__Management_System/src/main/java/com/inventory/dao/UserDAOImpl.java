package com.inventory.dao;

import com.inventory.model.User;
import com.inventory.util.DBConnection;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";

    @Override
    public boolean signup(User user) {
        String sql = "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getEmail());

            boolean success = ps.executeUpdate() > 0;

            if (success) {
                System.out.println(GREEN + "✅ Signup successful! You can now log in." + RESET);
            }
            return success;

        } catch (SQLException e) {
            System.out.println(RED + "⚠️ Signup failed. Unable to register the user." + RESET);

            return false;
        }
    }

    @Override
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                return user;
            } else {
                System.out.println(RED + "❌ Invalid username or password. Please try again." + RESET);
            }

        } catch (SQLException e) {
            System.out.println(RED + "⚠️ Login failed due to a database error." + RESET);
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                return user;
            } else {
                System.out.println(YELLOW + "⚠️ No user found with username: " + username + RESET);
            }

        } catch (SQLException e) {
            System.out.println(RED + "⚠️ Error while fetching user details." + RESET);
        }
        return null;
    }
}
