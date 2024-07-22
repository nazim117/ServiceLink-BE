package org.example.servicelinkbe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthCheckController {
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
}