package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.initialMoney.InitialMoneyPostRequest;
import com.monthlyexpenses.server.dto.request.initialMoney.InitialMoneyPutRequest;
import com.monthlyexpenses.server.service.InitialMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/initialMoney")
public class InitialMoneyController {

    private final InitialMoneyService initialMoneyService;

    @Autowired
    public InitialMoneyController(InitialMoneyService initialMoneyService) {
        this.initialMoneyService = initialMoneyService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createInitialMoney(@RequestHeader(value = "userId") Long userId,
                                                @Valid @RequestBody InitialMoneyPostRequest initialMoneyPostRequest) {
        return ResponseEntity.ok(initialMoneyService.createInitialMoney(userId, initialMoneyPostRequest.getYearNumber(),
                initialMoneyPostRequest.getMonth(), initialMoneyPostRequest.getInitialMoney()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateInitialMoney(@RequestHeader(value = "userId") Long userId,
                                                @Valid @RequestBody InitialMoneyPutRequest initialMoneyPutRequest) {
        return ResponseEntity.ok(initialMoneyService.updateInitialMoney(userId, initialMoneyPutRequest.getInitialMoneyId(),
                initialMoneyPutRequest.getInitialMoney()));
    }
}
