package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.initialMoney.InitialMoneyPostRequest;
import com.monthlyexpenses.server.dto.request.initialMoney.InitialMoneyPutRequest;
import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import com.monthlyexpenses.server.service.InitialMoneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/initialMoney")
@RequiredArgsConstructor
public class InitialMoneyController {

    private final InitialMoneyService initialMoneyService;

    @PostMapping
    public ResponseEntity<InitialMoneyResponse> createInitialMoney(@RequestHeader(value = "userId") Long userId,
                                                                   @Valid @RequestBody InitialMoneyPostRequest initialMoneyPostRequest) {
        return ok(initialMoneyService.createInitialMoney(userId, initialMoneyPostRequest.getYearNumber(),
                initialMoneyPostRequest.getMonth(), initialMoneyPostRequest.getInitialMoney()));
    }

    @PutMapping
    public ResponseEntity<InitialMoneyResponse> updateInitialMoney(@RequestHeader(value = "userId") Long userId,
                                                                   @Valid @RequestBody InitialMoneyPutRequest initialMoneyPutRequest) {
        return ok(initialMoneyService.updateInitialMoney(userId, initialMoneyPutRequest.getInitialMoneyId(),
                initialMoneyPutRequest.getInitialMoney()));
    }
}
