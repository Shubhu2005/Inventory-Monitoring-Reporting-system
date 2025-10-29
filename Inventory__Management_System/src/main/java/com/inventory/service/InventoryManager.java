package com.inventory.service;

import com.inventory.dao.ProductDAO;
import com.inventory.dao.ProductDAOImpl;
import com.inventory.model.Product;
import com.inventory.exceptions.*;

import java.util.List;
import java.util.Scanner;

public class InventoryManager {

    private final ProductDAO productDAO = new ProductDAOImpl();
    private final Scanner scanner;

    public InventoryManager(Scanner scanner) {
        this.scanner = scanner;
    }

    // === ADD PRODUCT ===
    public void addProduct() {
        try {
            System.out.println("\n==== ADD PRODUCT ====");
            System.out.print("Enter Product ID: ");
            int id = readIntInput();

            Product existing = productDAO.getProductById(id);
            if (existing != null) {
                System.out.println("⚠ Product ID already exists. Please try another ID.");
                return;
            }

            System.out.print("Enter Product Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Product Price: ");
            double price = readDoubleInput();
            System.out.print("Enter Product Category: ");
            String category = scanner.nextLine().trim();
            System.out.print("Enter Product Quantity: ");
            int quantity = readIntInput();

            if (name.isEmpty() || category.isEmpty()) {
                System.out.println("⚠ Product name or category cannot be empty.");
                return;
            }
            if (price < 0 || quantity < 0) {
                System.out.println("⚠ Price and quantity must be non-negative values.");
                return;
            }

            Product product = new Product(id, name, price, category, quantity);
            boolean added = productDAO.addProduct(product);
            System.out.println(added ? "✅ Product added successfully." : "⚠ Product could not be added. Try again.");

        } catch (Exception e) {
            System.out.println("⚠ Something went wrong while adding the product. Please try again.");
        }
    }

    // === REMOVE PRODUCT ===
    public void removeProduct() {
        try {
            System.out.print("\nEnter Product ID to remove: ");
            int id = readIntInput();
            Product existing = productDAO.getProductById(id);
            if (existing == null) {
                System.out.println("⚠ Product not found with ID: " + id);
                return;
            }

            boolean removed = productDAO.removeProduct(id);
            System.out.println(removed ? "✅ Product removed successfully." : "⚠ Could not remove product. Try again.");

        } catch (Exception e) {
            System.out.println("⚠ Something went wrong while removing the product. Please try again.");
        }
    }

    // === UPDATE PRODUCT ===
    public void updateProduct() {
        try {
            System.out.print("\nEnter Product ID to update: ");
            int id = readIntInput();
            Product existing = productDAO.getProductById(id);
            if (existing == null) {
                System.out.println("⚠ Product not found with ID: " + id);
                return;
            }

            System.out.print("Enter new Name [" + existing.getName() + "]: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) name = existing.getName();

            System.out.print("Enter new Price [" + existing.getPrice() + "]: ");
            String priceStr = scanner.nextLine().trim();
            double price = priceStr.isEmpty() ? existing.getPrice() : Double.parseDouble(priceStr);

            System.out.print("Enter new Category [" + existing.getCategory() + "]: ");
            String category = scanner.nextLine().trim();
            if (category.isEmpty()) category = existing.getCategory();

            System.out.print("Enter new Quantity [" + existing.getQuantity() + "]: ");
            String qtyStr = scanner.nextLine().trim();
            int quantity = qtyStr.isEmpty() ? existing.getQuantity() : Integer.parseInt(qtyStr);

            if (price < 0 || quantity < 0) {
                System.out.println("⚠ Price and quantity must be non-negative values.");
                return;
            }

            Product updated = new Product(id, name, price, category, quantity);
            boolean ok = productDAO.updateProduct(updated);
            System.out.println(ok ? "✅ Product updated successfully." : "⚠ Update failed. Please try again.");

        } catch (NumberFormatException e) {
            System.out.println("⚠ Please enter valid numeric values for price and quantity.");
        } catch (Exception e) {
            System.out.println("⚠ Something went wrong while updating the product. Please try again.");
        }
    }

    // === DISPLAY ALL PRODUCTS ===
    public void displayAllProducts() {
        try {
            List<Product> products = productDAO.getAllProducts();
            if (products.isEmpty()) {
                System.out.println("⚠ No products available in the inventory.");
            } else {
                printProductTable(products);
            }
        } catch (Exception e) {
            System.out.println("⚠ Unable to display products right now. Please try again later.");
        }
    }

    // === SEARCH BY ID ===
    public void searchById() {
        try {
            System.out.print("Enter Product ID: ");
            int id = readIntInput();
            Product p = productDAO.getProductById(id);
            if (p == null) {
                System.out.println("⚠ No product found with ID: " + id);
            } else {
                printProductTable(List.of(p));
            }
        } catch (Exception e) {
            System.out.println("⚠ Invalid input. Please enter a valid product ID.");
        }
    }

    // === SEARCH BY CATEGORY ===
    public void searchByCategory() {
        try {
            System.out.print("Enter Category: ");
            String category = scanner.nextLine().trim();
            List<Product> products = productDAO.getProductsByCategory(category);
            if (products.isEmpty()) {
                System.out.println("⚠ No products found in this category.");
            } else {
                printProductTable(products);
            }
        } catch (Exception e) {
            System.out.println("⚠ Something went wrong while searching by category.");
        }
    }

    // === FILTER BY PRICE RANGE ===
    public void filterByPriceRange() {
        try {
            System.out.print("Enter Minimum Price: ");
            double min = readDoubleInput();
            System.out.print("Enter Maximum Price: ");
            double max = readDoubleInput();

            if (min > max) {
                System.out.println("⚠ Minimum price cannot be greater than maximum price.");
                return;
            }

            List<Product> products = productDAO.getProductsByPriceRange(min, max);
            if (products.isEmpty()) {
                System.out.println("⚠ No products found in that price range.");
            } else {
                printProductTable(products);
            }
        } catch (Exception e) {
            System.out.println("⚠ Invalid input. Please enter valid price values.");
        }
    }

    // === UTILITY METHODS ===
    private void printProductTable(List<Product> list) {
        System.out.println();
        System.out.printf("%-5s %-25s %-10s %-15s %-10s\n", "ID", "NAME", "PRICE", "CATEGORY", "QUANTITY");
        System.out.println("---------------------------------------------------------------");
        if (list == null || list.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product p : list) {
                System.out.printf("%-5d %-25s %-10.2f %-15s %-10d\n",
                        p.getId(), p.getName(), p.getPrice(), p.getCategory(), p.getQuantity());
            }
        }
        System.out.println();
    }

    private int readIntInput() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("⚠ Please enter a valid number: ");
            }
        }
    }

    private double readDoubleInput() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("⚠ Please enter a valid number: ");
            }
        }
    }
}
