package com.ml.record.service;

import java.util.List;
import java.util.Optional;
import com.ml.record.exception.RecordException;
import com.ml.record.kafka.MessageProducer;
import com.ml.record.model.Record;
import com.ml.record.repository.RecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private MessageProducer messageProducer;

    @CacheEvict(cacheNames = "Record", key = "#record.cpf")
    public Record saveRecord(Record record) {
        Record recordSaved = recordRepository.save(record);
        if (recordSaved != null && !recordSaved.getCpf().isBlank()) {
            messageProducer.sendMessage(recordSaved);
        }
        return recordSaved;
    }

    @Cacheable(cacheNames = "Record", key = "#cpf")
    public Optional<Record> findRecordByCpf(String cpf) throws RecordException {
        if (!validaCpf(cpf)) {
            throw new RecordException("O CPF informado não tem um formato válido", HttpStatus.BAD_REQUEST);
        }
        Optional<Record> opRecord = recordRepository.findById(cpf);
        if (!opRecord.isPresent()) {
            throw new RecordException(null, HttpStatus.NO_CONTENT);
        }
        return opRecord;
    }

    @Cacheable(cacheNames = "Record", key = "#root.method.name")
    public List<Record> findAllRecord() {
        System.out.println("Entroooooooou");
        List<Record> records = Streamable.of(recordRepository.findAll()).toList();
        if(records.isEmpty()) {
            throw new RecordException(null, HttpStatus.NO_CONTENT);
        }
        return records;
    }

    private boolean validaCpf(String cpf) {
        int cpfValidLength = 11;
        return !cpf.isEmpty() && cpf.length() == cpfValidLength
                && cpf.chars().allMatch(Character::isDigit);
    }
}
