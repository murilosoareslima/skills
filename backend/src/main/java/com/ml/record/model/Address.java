package com.ml.record.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {
    
    private static final String CEP_INVALID = "O cep informado não é válido";
    private static final String STREET_INVALID = "O logradouro informado não é válido";
    private static final String NUMBER_INVALID = "O número da residência informado, não é válido";
    private static final String DISTRICT_INVALID = "O bairro informado não é válido";
    private static final String CITY_INVALID = "A cidade informada não é válida";
    private static final String STATE_INVALID = "O estado informado não é válido";

    @NotBlank(message = CEP_INVALID)
    @Size(min = 8, max = 8, message = CEP_INVALID)    
    private String cep;

    @NotBlank(message = STREET_INVALID)
    private String street;

    @Min(value = 1, message = NUMBER_INVALID)
    private int number;

    @NotBlank(message = DISTRICT_INVALID)
    private String district;

    @NotBlank(message = CITY_INVALID)
    @Size(min = 3, message = CITY_INVALID)
    private String city;

    @NotBlank(message = STATE_INVALID)
    @Size(min = 2, message = STATE_INVALID)
    private String state;
}
