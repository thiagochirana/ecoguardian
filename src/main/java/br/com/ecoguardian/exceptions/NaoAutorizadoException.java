package br.com.ecoguardian.exceptions;

public class NaoAutorizadoException extends Exception{

    public NaoAutorizadoException(){
        super();
    }

    public NaoAutorizadoException(String mensagem){
        super(mensagem);
    }

    public NaoAutorizadoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}
