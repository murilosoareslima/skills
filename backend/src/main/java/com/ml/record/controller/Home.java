package com.ml.record.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;

@RestController
@RequestMapping(value = {"/", "", "/home"})
@Log
public class Home {
    
    @ApiOperation(value = "Verifica se a Api está respondendo")
    @GetMapping
    public ResponseEntity<String> deployed() {
        log.info("Consulta padrão realizada");
        return new ResponseEntity<>("Api is running", HttpStatus.OK);
    }
}
