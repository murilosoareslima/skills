package com.ml.record.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.junit.Before;
import org.junit.Test;

public class RecordTest {

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private CPFValidator cpfValidator = new CPFValidator();

    @Before
    public void setup() {
        cpfValidator.initialize(null);
    }

    @Test
    public void shouldNotPassWithoutInformation() {
        Record record = new Record();
        Set<ConstraintViolation<Record>> validate = validator.validate(record);
        String messageExpected = "A idade informada não é válida";
        String messageError = validate.stream().filter(v -> v.getMessage().equals(messageExpected))
                .map(ConstraintViolation::getMessage).findFirst().get();

        assertFalse(validate.isEmpty());
        assertEquals(messageExpected, messageError);
    }

    @Test
    public void shouldNotPassMissInformation() {
        Record record = new Record();
        record.setName("Nome Exemplo");
        record.setAge(18);
        record.setCpf("27348324029");
        Set<ConstraintViolation<Record>> validate = validator.validate(record);
        boolean cpfIsValid = cpfValidator.isValid(record.getCpf(), null);

        assertFalse(validate.isEmpty());
        assertTrue(cpfIsValid);
    }

    @Test
    public void shouldNotPassCpfInvalid() {
        Record record = new Record();
        record.setName("Nome Exemplo");
        record.setAge(18);
        record.setCpf("39912885554");
        Set<ConstraintViolation<Record>> validate = validator.validate(record);
        boolean cpfIsValid = cpfValidator.isValid(record.getCpf(), null);

        assertFalse(validate.isEmpty());
        assertFalse(cpfIsValid);
    }

    @Test
    public void shouldPassRecordValid() {
        Record record = new Record();
        record.setName("Nome Exemplo");
        record.setAge(18);
        record.setCpf("11111111111");
        Set<ConstraintViolation<Record>> validate = validator.validate(record);
        boolean cpfIsValid = cpfValidator.isValid(record.getCpf(), null);
        assertTrue(validate.isEmpty());
        assertTrue(cpfIsValid);
    }
}
