package com.ml.record.controller;

import java.util.List;
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

import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/record")
@Log
public class RecordController {

    @Autowired
    private RecordService recordService;

    @ApiOperation(value = "Salva um cadastro", notes = "Salva um novo cadastro no Elasticsearch e envia uma mensagem à um topico do Kafka")    
    @PostMapping
    public ResponseEntity<Response<Record>> save(@Valid @RequestBody Record record,
            BindingResult result) {
        Response<Record> response = new Response<>();
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(e -> response.addErrorMsgToResponse(e.getDefaultMessage()));
            log.warning("Erro no body da requisição " + response.getErrors().get(0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Record savedRecord = recordService.saveRecord(record);
        response.setData(savedRecord);
        log.info("Cadastro salvo com sucesso!");
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Consulta por CPF", notes = "Busca um cadastro por CPF no ElasticSearch")
    @GetMapping(value = "/{cpf}")    
    public ResponseEntity<Response<Record>> findRecordByCpf(@PathVariable String cpf) throws RecordException {
        Response<Record> response = new Response<>();
        Optional<Record> opRecord = recordService.findRecordByCpf(cpf);
        Record record = opRecord.get();
        log.info("Encontrado o cadastro com cpf: " + cpf);
        response.setData(record);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Consulta todos os cadastros", notes = "Busca todos os cadastros salvos na base")
    @GetMapping    
    public ResponseEntity<List<Record>> findAllRecord() {
        List<Record> records = recordService.findAllRecord();        
        log.info("Retornando " + records.size() + " cadastros encontrados!");
        return ResponseEntity.ok(records);
    }

}
