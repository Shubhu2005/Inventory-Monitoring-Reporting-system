package com.inventory;

import com.inventory.model.User;
import com.inventory.model.Product;
import com.inventory.service.InventoryManager;
import com.inventory.service.UserService;
import com.inventory.service.StockAlertService;
import com.inventory.dao.ProductDAOImpl;
import com.inventory.util.CSVHelper;
import com.inventory.util.EmailUtil;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    // Scheduler to run stock alerts periodically
    private static ScheduledExecutorService scheduler;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService(scanner);
        InventoryManager inventoryManager = new InventoryManager(scanner);

        while (true) {
            System.out.println("\n==== INVENTORY SYSTEM ====");
            System.out.println("[1] Login");
            System.out.println("[2] Signup");
            System.out.println("[3] Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    User user = userService.login();
                    if (user != null) {
                        // ✅ Start Stock Alert Scheduler for the logged-in user
                        startStockAlertScheduler(user);

                        if (user.getRole().equalsIgnoreCase("admin"))
                            adminMenu(scanner, inventoryManager, user);
                        else
                            userMenu(scanner, inventoryManager);

                        // ✅ Stop scheduler when user exits menu
                        stopStockAlertScheduler();
                    }
                    break;

                case "2":
                    userService.signup();
                    break;

                case "3":
                    System.out.println("Exiting...");
                    stopStockAlertScheduler();
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ✅ ADMIN MENU
    private static void adminMenu(Scanner scanner, InventoryManager im, User user) {
        while (true) {
            System.out.println("\n==== ADMIN MENU ====");
            System.out.println("[1] Add Product");
            System.out.println("[2] Remove Product");
            System.out.println("[3] Update Product");
            System.out.println("[4] Display All Products");
            System.out.println("[5] Filter by Price Range");
            System.out.println("[6] Generate & Email Report");
            System.out.println("[7] Back");
            System.out.print("Enter choice: ");
            String ch = scanner.nextLine().trim();

            switch (ch) {
                case "1": im.addProduct(); break;
                case "2": im.removeProduct(); break;
                case "3": im.updateProduct(); break;
                case "4": im.displayAllProducts(); break;
                case "5": im.filterByPriceRange(); break;
                case "6": generateReportAndSend(user); break;
                case "7": return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    // ✅ USER MENU
    private static void userMenu(Scanner scanner, InventoryManager im) {
        while (true) {
            System.out.println("\n==== USER MENU ====");
            System.out.println("[1] View All Products");
            System.out.println("[2] Filter by Price Range");
            System.out.println("[3] Back");
            System.out.print("Enter choice: ");
            String ch = scanner.nextLine().trim();

            switch (ch) {
                case "1": im.displayAllProducts(); break;
                case "2": im.filterByPriceRange(); break;
                case "3": return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    // ✅ Generate CSV Report and send via Email
    private static void generateReportAndSend(User user) {
        ProductDAOImpl dao = new ProductDAOImpl();
        List<Product> products = dao.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products to generate report.");
            return;
        }

        String filePath = CSVHelper.saveProductReport(products, user.getUsername());
        if (filePath != null) {
            EmailUtil.sendReport(user.getEmail(), "Inventory Report", "Attached is the latest report.", filePath);
        }
    }

    // ✅ Start Stock Alert Service for logged-in user
    private static void startStockAlertScheduler(User loggedInUser) {
        StockAlertService alertService = new StockAlertService(loggedInUser);
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(alertService::checkStockAlerts, 0, 5, TimeUnit.MINUTES);
        System.out.println("🕒 Stock Alert Scheduler started for " + loggedInUser.getUsername());
    }

    // ✅ Stop scheduler when user logs out or exits
    private static void stopStockAlertScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            System.out.println("🛑 Stock Alert Scheduler stopped.");
        }
    }
}
