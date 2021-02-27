package com.monthlyexpenses.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/startHeroku")
public class StartHerokuController {

    @GetMapping("/start")
    public ResponseEntity<?> startHeroku() {
        return ResponseEntity.ok().build();
    }
}
