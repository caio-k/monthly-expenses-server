package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.initialMoney.InitialMoneyPostRequest;
import com.monthlyexpenses.server.dto.request.initialMoney.InitialMoneyPutRequest;
import com.monthlyexpenses.server.service.InitialMoneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/initialMoney")
@RequiredArgsConstructor
public class InitialMoneyController {

    private final InitialMoneyService initialMoneyService;

    @PostMapping
    public ResponseEntity<?> createInitialMoney(@RequestHeader(value = "userId") Long userId,
                                                @Valid @RequestBody InitialMoneyPostRequest initialMoneyPostRequest) {
        return ResponseEntity.ok(initialMoneyService.createInitialMoney(userId, initialMoneyPostRequest.getYearNumber(),
                initialMoneyPostRequest.getMonth(), initialMoneyPostRequest.getInitialMoney()));
    }

    @PutMapping
    public ResponseEntity<?> updateInitialMoney(@RequestHeader(value = "userId") Long userId,
                                                @Valid @RequestBody InitialMoneyPutRequest initialMoneyPutRequest) {
        return ResponseEntity.ok(initialMoneyService.updateInitialMoney(userId, initialMoneyPutRequest.getInitialMoneyId(),
                initialMoneyPutRequest.getInitialMoney()));
    }
}
