package com.ml.record.model;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Phone {
    
    private static final String DDD_INVALID = "O DDD informado não é válido";
    private static final String NUMBER_INVALID = "O telefone informado não é válido";

    @NotBlank(message = DDD_INVALID)
    private String ddd;

    @NotBlank(message = NUMBER_INVALID)
    private String number;
}
