package com.ml.record.controller;

import javax.validation.Valid;

import com.ml.record.model.Record;
import com.ml.record.response.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RecordController {

    @PostMapping("/record")
    public ResponseEntity<Response<Record>> save(@Valid @RequestBody Record record,
            BindingResult result) {
        Response<Record> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(e ->  response.addErrorMsgToResponse(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }        
        response.setData(record);
        return ResponseEntity.ok().body(response);
    }
}
