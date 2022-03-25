package com.ml.record.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private T data;
    
    private List<Object> errors;

    public void addErrorMsgToResponse(String msgError) {
        ResponseError error = new ResponseError().setDetails(msgError);
        if(errors == null) {
            errors = new ArrayList<>();
        }
        getErrors().add(error);
    }
}
