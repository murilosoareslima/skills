package com.ml.record.util;

import java.util.ArrayList;
import java.util.List;

import com.ml.record.model.Address;
import com.ml.record.model.Phone;
import com.ml.record.model.Record;

public class RecordBuildTest {
    
    private static List<Address> buildAdress(boolean hasValidCity) {
        Address adress = new Address();
        adress.setCep("13300000");
        adress.setStreet("Rua Exemplo");
        adress.setDistrict("Bairro Exemplo");
        adress.setNumber(100);
        adress.setCity(hasValidCity ? "Itu" : "It");
        adress.setState("SP");
        List<Address> adressList = new ArrayList<>();
        adressList.add(adress);
        return adressList;
    }

    private static List<Phone> buildPhones() {
        Phone phone = new Phone();
        phone.setDdd("011");
        phone.setNumber("78225585");
        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(phone);
        return phoneList;
    }

    public static Record buildRecord(boolean hasValidCpf,
            boolean hasValidCity, boolean hasPhone) {
        Record record = new Record();
        record.setName("Nome Exemplo");
        record.setAge(18);
        record.setCpf(hasValidCpf ? "27348324029" : "39912885554");
        record.setAddresses(buildAdress(hasValidCity));
        if (hasPhone) {
            record.setPhones(buildPhones());
        }
        return record;
    }
}
