package com.inventory.dao;

import com.inventory.model.Product;
import java.util.List;

public interface ProductDAO {
    boolean addProduct(Product p);
    boolean removeProduct(int id);
    boolean updateProduct(Product p);
    Product getProductById(int id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByPriceRange(double min, double max);
}
