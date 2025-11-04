package com.devops.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "spring-devops-demo");
        health.put("version", "1.0.0");
        return health;
    }

    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Spring DevOps Demo API");
        response.put("timestamp", LocalDateTime.now());
        response.put("endpoints", new String[] {
                "/health - System health check",
                "/actuator/health - Spring Boot Actuator health",
                "/api/products - Get all products",
                "/api/products/{id} - Get product by ID",
                "/api/products/category/{category} - Get products by category"
        });
        return response;
    }
}