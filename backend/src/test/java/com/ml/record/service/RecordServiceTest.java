package com.ml.record.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import com.ml.record.exception.RecordException;
import com.ml.record.model.Record;
import com.ml.record.repository.RecordRepository;
import com.ml.record.util.RecordBuildTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RecordServiceTest {

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private RecordService recordService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldNotPassCpfFormatInvalid() {
        String cpf = "1111111111g";
        try {
            recordService.findByCpf(cpf);
            fail("Não deve chegar aqui");
        } catch (RecordException e) {
            assertNotNull(e.getMessage());
            assertEquals("O CPF informado não tem um formato válido", e.getMessage());
        }
    }

    @Test
    public void shouldNotPassCpfSizeInvalid() {
        String cpf = "1111111111";
        try {
            recordService.findByCpf(cpf);
            fail("Não deve chegar aqui");
        } catch (RecordException e) {
            assertNotNull(e.getMessage());
            assertEquals("O CPF informado não tem um formato válido", e.getMessage());
        }
    }

    @Test
    public void shouldPassCpfValid() {
        String cpf = "27348324029";
        try {            
            Optional<Record> opRecord = recordService.findByCpf(cpf);

            Mockito.verify(recordRepository, Mockito.times(1)).findById(cpf);
            Mockito.when(recordRepository.findById(cpf)).thenReturn(opRecord);          
        } catch (RecordException e) {
            fail("Não deve chegar aqui");
        }
    }

    @Test
    public void shouldSaveRecordValid() {
        Record record = RecordBuildTest.buildRecord(true, true, true);
        recordService.save(record);

        Mockito.verify(recordRepository, Mockito.times(1)).save(record);
        Mockito.when(recordRepository.save(record)).thenReturn(new Record());
    }

}
