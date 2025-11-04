package com.devops.demo.service;

import com.devops.demo.model.Product;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {

    private List<Product> products;

    public ProductService() {
        initializeProducts();
    }

    private void initializeProducts() {
        products = new ArrayList<>();
        products.add(new Product(1L, "Laptop", "High-performance laptop for developers", 1299.99, "Electronics"));
        products.add(new Product(2L, "Coffee Mug", "Perfect mug for coding sessions", 19.99, "Accessories"));
        products.add(new Product(3L, "Mechanical Keyboard", "RGB mechanical keyboard", 149.99, "Electronics"));
        products.add(new Product(4L, "Standing Desk", "Adjustable height standing desk", 599.99, "Furniture"));
        products.add(new Product(5L, "Monitor", "27-inch 4K monitor", 399.99, "Electronics"));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Optional<Product> getProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    public List<Product> getProductsByCategory(String category) {
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    public int getTotalProductCount() {
        return products.size();
    }
}