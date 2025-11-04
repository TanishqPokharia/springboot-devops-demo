package com.devops.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SpringDevopsDemoApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring Boot application context loads
        // successfully
    }
}