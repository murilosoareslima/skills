package com.ml.record.controller;

import java.util.Optional;

import javax.validation.Valid;

import com.ml.record.exception.RecordException;
import com.ml.record.model.Record;
import com.ml.record.response.Response;
import com.ml.record.service.RecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/record")
@Log
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping
    public ResponseEntity<Response<Record>> save(@Valid @RequestBody Record record,
            BindingResult result) {
        Response<Record> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(e -> response.addErrorMsgToResponse(e.getDefaultMessage()));
            log.warning("Erro no body da requisição " + response.getErrors().get(0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Record savedRecord = null;
        try {
            savedRecord = recordService.save(record);
            response.setData(savedRecord);
        } catch (Exception ex) {
            String msgError = "Erro de servidor ao tentar salvar o cadastro \n" + ex.getCause();
            response.addErrorMsgToResponse(msgError);
            return ResponseEntity.internalServerError().body(response);
        }        
        log.info("Cadastro salvo com sucesso!");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{cpf}")
    public ResponseEntity<Response<Record>> findByCpf(@PathVariable String cpf) {
        Response<Record> response = new Response<>();
        Optional<Record> opRecord = Optional.empty();
        Record record = null;
        try {
            opRecord = recordService.findByCpf(cpf);
            if (opRecord.isPresent()) {
                record = opRecord.get();
            } else {
                String msgError = "Não encontrado um cadastro com cpf: " + cpf;
                log.info(msgError);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } catch (RecordException ex) {
            String msgError = "O Cpf informado não é válido: " + cpf;
            log.warning(msgError);
            response.addErrorMsgToResponse(msgError);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            String msgError = "Erro de servidor ao tentar fazer a consulta\n" + ex.getCause();
            log.warning(msgError);
            response.addErrorMsgToResponse(msgError);
            return ResponseEntity.internalServerError().body(response);
        }
        log.info("Encontrado o cadastro com cpf: " + cpf);
        response.setData(record);
        return ResponseEntity.ok().body(response);
    }

}
