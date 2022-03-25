package com.ml.record.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

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
        Record record = buildRecord(true, true, false);
        Set<ConstraintViolation<Record>> validate = validator.validate(record);

        assertFalse(validate.isEmpty());
    }

    @Test
    public void shouldNotPassInValidatorCpfInvalid() {
        Record record = buildRecord(false, true, true);
        Set<ConstraintViolation<Record>> validate = validator.validate(record);
        String messageExpected = "O CPF informado não é válido";
        String messageError = validate.stream().filter(v -> v.getMessage().equals(messageExpected))
                .map(ConstraintViolation::getMessage).findFirst().get();

        assertFalse(validate.isEmpty());        
        assertEquals(messageExpected, messageError);
    }

    @Test
    public void shouldNotPassCityInvalid() {
        Record record = buildRecord(true, false, true);
        Set<ConstraintViolation<Record>> validate = validator.validate(record);

        assertFalse(validate.isEmpty());
    }

    @Test
    public void shouldPassRecordIsValid() {
        Record record = buildRecord(true, true, true);
        Set<ConstraintViolation<Record>> validate = validator.validate(record);

        assertTrue(validate.isEmpty());
    }

    private List<Adress> buildAdress(boolean hasValidCity) {
        Adress adress = new Adress();
        adress.setCep("13300000");
        adress.setStreet("Rua Exemplo");
        adress.setDistrict("Bairro Exemplo");
        adress.setNumber(100);
        adress.setCity(hasValidCity ? "Itu" : "It");
        adress.setState("SP");
        List<Adress> adressList = new ArrayList<>();
        adressList.add(adress);
        return adressList;
    }

    private List<Phone> buildPhones() {
        Phone phone = new Phone();
        phone.setDdd("011");
        phone.setNumber("78225585");
        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(phone);
        return phoneList;
    }

    private Record buildRecord(boolean hasValidCpf,
            boolean hasValidCity, boolean hasPhone) {
        Record record = new Record();
        record.setName("Nome Exemplo");
        record.setAge(18);
        record.setCpf(hasValidCpf ? "27348324029" : "39912885554");
        record.setAdresses(buildAdress(hasValidCity));
        if (hasPhone) {
            record.setPhones(buildPhones());
        }
        return record;
    }
}
