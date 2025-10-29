package com.inventory.dao;

import com.inventory.model.Product;
import com.inventory.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";

    @Override
    public boolean addProduct(Product p) {
        String sql = "INSERT INTO products (id, name, price, category, quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getName());
            stmt.setDouble(3, p.getPrice());
            stmt.setString(4, p.getCategory());
            stmt.setInt(5, p.getQuantity());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(RED + "⚠️ Unable to add product. Please check your input or database connection." + RESET);

            return false;
        }
    }

    @Override
    public boolean removeProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(RED + "⚠️ Error while removing product. Please verify product ID." + RESET);
            System.out.println(YELLOW + "💡 Hint: " + e.getMessage() + RESET);
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product p) {
        String sql = "UPDATE products SET name=?, price=?, category=?, quantity=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getName());
            stmt.setDouble(2, p.getPrice());
            stmt.setString(3, p.getCategory());
            stmt.setInt(4, p.getQuantity());
            stmt.setInt(5, p.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(RED + "⚠️ Failed to update product details. Please try again." + RESET);
            System.out.println(YELLOW + "💡 Hint: " + e.getMessage() + RESET);
            return false;
        }
    }

    @Override
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            System.out.println(RED + "⚠️ Error while fetching product by ID." + RESET);
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.out.println(RED + "⚠️ Unable to retrieve product list from database." + RESET);
        }
        return list;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.out.println(RED + "⚠️ Error while searching products by category." + RESET);
        }
        return list;
    }

    @Override
    public List<Product> getProductsByPriceRange(double min, double max) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE price BETWEEN ? AND ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, min);
            stmt.setDouble(2, max);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.out.println(RED + "⚠️ Failed to fetch products in the given price range." + RESET);

        }
        return list;
    }
}
