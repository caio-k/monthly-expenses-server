package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.response.settings.SettingsResponse;
import com.monthlyexpenses.server.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping
    public ResponseEntity<SettingsResponse> getInitializationData(@RequestHeader(value = "userId") Long userId) {
        return ok(settingsService.findAllYearsAndExpensesTypeByCustomerId(userId));
    }
}
