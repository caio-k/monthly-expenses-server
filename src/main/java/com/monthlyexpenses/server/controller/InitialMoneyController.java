package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.initialMoney.InitialMoneyGetRequest;
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

    @GetMapping("/get")
    public ResponseEntity<?> getInitialMoneyByYear(@Valid @RequestBody InitialMoneyGetRequest initialMoneyGetRequest) {
        return initialMoneyService.getInitialMoneyByYear(initialMoneyGetRequest.getUserId(), initialMoneyGetRequest.getYearId());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createInitialMoney(@Valid @RequestBody InitialMoneyPostRequest initialMoneyPostRequest) {
        return initialMoneyService.createInitialMoney(initialMoneyPostRequest.getUserId(), initialMoneyPostRequest.getYearId(),
                initialMoneyPostRequest.getMonth(), initialMoneyPostRequest.getInitialMoney());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateInitialMoney(@Valid @RequestBody InitialMoneyPutRequest initialMoneyPutRequest) {
        return initialMoneyService.updateInitialMoney(initialMoneyPutRequest.getUserId(), initialMoneyPutRequest.getInitialMoneyId(),
                initialMoneyPutRequest.getInitialMoney());
    }
}
