package com.ml.record.service;

import java.util.Optional;
import com.ml.record.exception.RecordException;
import com.ml.record.kafka.MessageProducer;
import com.ml.record.model.Record;
import com.ml.record.repository.RecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private MessageProducer messageProducer;

    public Record save(Record record) {        
        Record recordSaved = recordRepository.save(record);
        if(recordSaved != null && !recordSaved.getCpf().isBlank()) {            
            messageProducer.sendMessage(recordSaved);
        }
        return recordSaved;
    }

    public Optional<Record> findByCpf(String cpf) throws RecordException {
        if (!validaCpf(cpf)) {
            throw new RecordException("O CPF informado não tem um formato válido");
        }        
        return recordRepository.findById(cpf);
    }

    private boolean validaCpf(String cpf) {
        int cpfValidLength = 11;
        return !cpf.isEmpty() && cpf.length() == cpfValidLength
                && cpf.chars().allMatch(Character::isDigit);
    }
}
