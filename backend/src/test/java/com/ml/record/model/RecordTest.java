package com.ml.record.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.ml.record.util.RecordBuildTest;

import org.junit.Test;

public class RecordTest {

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void shouldNotPassInValidatorWithoutInformation() {
        Record record = new Record();
        Set<ConstraintViolation<Record>> validate = validator.validate(record);
        String messageExpected = "A idade informada não é válida";
        String messageError = validate.stream().filter(v -> v.getMessage().equals(messageExpected))
                .map(ConstraintViolation::getMessage).findFirst().get();

        assertFalse(validate.isEmpty());
        assertEquals(messageExpected, messageError);
    }

    @Test
    public void shouldNotPassInValidatorHasNoPhone() {
        Record record = RecordBuildTest.buildRecord(true, true, false);
        Set<ConstraintViolation<Record>> validate = validator.validate(record);

        assertFalse(validate.isEmpty());
    }

    @Test
    public void shouldNotPassInValidatorCpfInvalid() {
        Record record = RecordBuildTest.buildRecord(false, true, true);
        Set<ConstraintViolation<Record>> validate = validator.validate(record);
        String messageExpected = "O CPF informado não é válido";
        String messageError = validate.stream().filter(v -> v.getMessage().equals(messageExpected))
                .map(ConstraintViolation::getMessage).findFirst().get();

        assertFalse(validate.isEmpty());        
        assertEquals(messageExpected, messageError);
    }

    @Test
    public void shouldNotPassCityInvalid() {
        Record record = RecordBuildTest.buildRecord(true, false, true);
        Set<ConstraintViolation<Record>> validate = validator.validate(record);

        assertFalse(validate.isEmpty());
    }

    @Test
    public void shouldPassRecordIsValid() {
        Record record = RecordBuildTest.buildRecord(true, true, true);
        Set<ConstraintViolation<Record>> validate = validator.validate(record);

        assertTrue(validate.isEmpty());
    }
}
