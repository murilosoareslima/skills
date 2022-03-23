package com.ml.record.model;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Record {
    
    private static final String NAME_INVALID = "O nome informado não é válido";
    private static final String AGE_INVALID = "A idade informada não é válida";
    private static final String CPF_INVALID = "O CPF informado não é válido";
    
    @NotBlank(message = NAME_INVALID)
    private String name;

    @Min(value = 1, message = AGE_INVALID)
    private int age;

    @CPF(message = CPF_INVALID)
    @NotBlank(message = CPF_INVALID)
    private String cpf;
    
    private List<Phone> phones;

    private List<Adress> adresses;

}
