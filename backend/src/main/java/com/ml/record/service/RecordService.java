package com.ml.record.service;

import java.util.Optional;

import com.ml.record.model.Record;
import com.ml.record.repository.RecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {
        
    @Autowired
    private RecordRepository recordRepository;
    
    public Record save(Record record) {
        return recordRepository.save(record);
    }

    public Optional<Record> findByCpf(String cpf) {
        return recordRepository.findById(cpf);
    }
}
