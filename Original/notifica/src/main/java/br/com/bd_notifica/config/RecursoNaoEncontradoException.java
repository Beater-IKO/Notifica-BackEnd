package main.java.br.com.bd_notifica.config;



@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNaoEncontradoException extends RuntimeException {


        public RecursoNaoEncontradoException(String mensagem){
            super(mensagem);
        }




}
