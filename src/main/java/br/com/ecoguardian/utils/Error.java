package br.com.ecoguardian.utils;

import br.com.ecoguardian.controllers.ViewBase;
import br.com.ecoguardian.exceptions.NaoAutorizadoException;
import br.com.ecoguardian.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class Error extends ResponseEntityExceptionHandler{

    @Autowired
    private ViewBase base;

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptions(Exception ex, WebRequest request) {
        return base.mensagemDeErro(ex, 500);
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(NotFoundException ex, WebRequest request) {
        return base.mensagemDeErro(ex, 404);
    }
//
//    @ExceptionHandler({Exception.class})
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ResponseEntity<Object> handleForbiddenException(Exception ex, WebRequest request) {
//        return new ResponseEntity<>("/ops/403", HttpStatus.FORBIDDEN);
//    }
//
    @ExceptionHandler({NaoAutorizadoException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView handleUnauthorizedException(NaoAutorizadoException ex, WebRequest request) {
        return base.mensagemDeErro(ex, 401);
    }
}
