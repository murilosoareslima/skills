package com.ml.record.exception;

import com.ml.record.response.Response;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RecordException.class)
    public ResponseEntity<Response<Object>> handlerRecordException(RecordException rex) {
        return buildResponseEntity(rex.getMessage(), rex.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String msgError = "O JSON da requisição está em um formato inválido: " + ex.getCause();
        return new ResponseEntity<>(buildResponse(msgError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Response<Object>> databaseConnectionFailsException(Exception ex) {
        String msgError = "Erro de servidor ao tentar fazer a consulta: " + ex.getCause();
        return buildResponseEntity(msgError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Response<Object>> buildResponseEntity(String message, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(buildResponse(message));
    }

    private Response<Object> buildResponse(String message) {
        Response<Object> response = new Response<>();
        response.addErrorMsgToResponse(message);
        return response;
    }
}
