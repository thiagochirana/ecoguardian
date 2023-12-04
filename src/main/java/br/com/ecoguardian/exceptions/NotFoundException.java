package br.com.ecoguardian.exceptions;

public class NotFoundException extends Exception{

    public NotFoundException(){
        super();
    }

    public NotFoundException(String mensagem){
        super(mensagem);
    }

    public NotFoundException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
