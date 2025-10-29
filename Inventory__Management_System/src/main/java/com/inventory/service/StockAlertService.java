package com.inventory.service;

import com.inventory.dao.ProductDAOImpl;
import com.inventory.model.Product;
import com.inventory.model.User;
import com.inventory.util.EmailUtil;

import java.util.List;

public class StockAlertService {

    private final ProductDAOImpl productDAO = new ProductDAOImpl();
    private final User currentUser;  // ✅ store logged-in user

    private static final int STOCK_THRESHOLD = 5;

    // Constructor
    public StockAlertService(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Checks all products for low stock and sends alert to the logged-in user.
     */
    public void checkStockAlerts() {
        try {
            List<Product> products = productDAO.getAllProducts();

            for (Product p : products) {
                if (p.getQuantity() <= STOCK_THRESHOLD) {
                    System.out.println("⚠ Low Stock Alert: " + p.getName());
                    System.out.println("   Current Qty: " + p.getQuantity());
                    System.out.println("   Threshold: " + STOCK_THRESHOLD);

                    String subject = "⚠ Low Stock Alert for " + p.getName();
                    String body = "Dear " + currentUser.getUsername() + ",\n\n" +
                            "Product: " + p.getName() +
                            "\nCurrent Quantity: " + p.getQuantity() +
                            "\nThreshold: " + STOCK_THRESHOLD +
                            "\n\nPlease restock this product soon.\n\n" +
                            "Regards,\nInventory Management System";

                    // Send email to current logged-in user
                    EmailUtil.sendReport(currentUser.getEmail(), subject, body, null);
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Error while checking stock alerts: " + e.getMessage());
        }
    }
}
