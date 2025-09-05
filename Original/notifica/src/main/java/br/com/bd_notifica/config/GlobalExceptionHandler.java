package main.java.br.com.bd_notifica.config;

import java.net.http.HttpClient;

import javax.smartcardio.ResponseAPDU;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handlerValidationException(ValidationException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação",
                ex.getMessage()

        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponsEntity<ErrorResponse> handlerValidationException(RecursoNaoEncontradoException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErrorResponse> handlerValidationException(RegraDeNegocioException ex){

        ErrorResponse errorResponse = new errorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Violação de regra de negócio",
        ex.getMessage()



        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);


    }
}
