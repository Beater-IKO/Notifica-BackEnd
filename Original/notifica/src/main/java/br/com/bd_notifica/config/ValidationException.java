package main.java.br.com.bd_notifica.config;



// Esta anotação é opcional, mas ajuda a deixar claro o status HTTP associado
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {


        public ValidationException(String mensagem){
            super(mensagem);
        }

}
