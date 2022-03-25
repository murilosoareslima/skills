package com.ml.record.response;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ResponseError {

    private static final String DETAIULS_ERROR=  "Detalhe do erro não pode ser null";
    
    @NotBlank(message = DETAIULS_ERROR)
    private String details;

}
