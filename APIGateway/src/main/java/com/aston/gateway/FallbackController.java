package com.aston.gateway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/users")
    public ResponseEntity<?> usersFallback() {
        return ResponseEntity.status(503).body(
                Map.of("message", "User service is currently unavailable")
        );
    }

    @GetMapping("/notifications")
    public ResponseEntity<?> notificationsFallback() {
        return ResponseEntity.status(503).body(
                Map.of("message", "Notification service is currently unavailable")
        );
    }
}