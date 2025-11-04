package com.devops.demo.controller;

import com.devops.demo.model.Product;
import com.devops.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Map<String, Object> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("totalCount", products.size());
        response.put("message", "Successfully retrieved all products");
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        Map<String, Object> response = new HashMap<>();

        if (product.isPresent()) {
            response.put("product", product.get());
            response.put("message", "Product found successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Product not found with ID: " + id);
            response.put("message", "Product not found");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category/{category}")
    public Map<String, Object> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("category", category);
        response.put("totalCount", products.size());
        response.put("message", "Successfully retrieved products for category: " + category);
        return response;
    }

    @GetMapping("/stats")
    public Map<String, Object> getProductStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", productService.getTotalProductCount());
        stats.put("categories", List.of("Electronics", "Accessories", "Furniture"));
        stats.put("message", "Product statistics retrieved successfully");
        return stats;
    }
}