package com.ml.record.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ml.record.validator.ListConstraint;

@Data
@NoArgsConstructor
@Document(indexName = "records")
public class Record implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = 4741996415523499829L;
    
    private static final String NAME_INVALID = "O nome informado não é válido";
    private static final String AGE_INVALID = "A idade informada não é válida";
    private static final String CPF_INVALID = "O CPF informado não é válido";
    private static final String PHONE_INVALID = "É necessário informar ao menos um telefone";
    private static final String ADRESS_INVALID = "É necessário informar ao menos um endereço";
        
    @NotBlank(message = NAME_INVALID)
    private String name;

    @Min(value = 1, message = AGE_INVALID)
    private int age;
    
    @CPF(message = CPF_INVALID)
    @NotBlank(message = CPF_INVALID)       
    @Id
    private String cpf;
    
    @ListConstraint(message = PHONE_INVALID)
    @Valid
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Phone> phones;

    @ListConstraint(message = ADRESS_INVALID)
    @Valid
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Address> addresses;

}
