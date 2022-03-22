package com.ml.record.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/", "", "/home"})
public class Home {
    
    @GetMapping
    public ResponseEntity<String> deployed() {
        return new ResponseEntity<>("Api is running", HttpStatus.OK);
    }
}
