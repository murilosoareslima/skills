package com.ml.record.service;

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
}
