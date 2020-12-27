package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    private final SettingsService settingsService;

    @Autowired
    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> getInitializationData(@RequestParam Long userId) {
        return settingsService.getInitializationData(userId);
    }
}
