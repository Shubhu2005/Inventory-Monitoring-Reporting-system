package com.inventory.util;

import com.inventory.model.Product;
import java.io.FileWriter;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CSVHelper {

    public static String saveProductReport(List<Product> products, String username) {
        String folderPath = "reports";
        File folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filePath = folderPath + File.separator + "inventory_report_" + username + "_" + timestamp + ".csv";

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID,Name,Category,Price,Quantity\n");
            for (Product p : products) {
                writer.append(p.getId() + "," + p.getName() + "," + p.getCategory() + "," + p.getPrice() + "," + p.getQuantity() + "\n");
            }
            writer.flush();
            System.out.println("✅ CSV report saved at " + filePath);
            return filePath;
        } catch (Exception e) {
            System.out.println("❌ Failed to save CSV: " + e.getMessage());
            return null;
        }
    }
}
