package br.com.ecoguardian.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Log {
    private static Logger LOG;

    public Log(Class classe){
        LOG = LoggerFactory.getLogger(classe);
    }

    //Exibir as requests recebidas no Controller
    public void request(HttpServletRequest request){
        String params = request.getParameterMap().entrySet().stream()
                .map(entry -> entry.getKey() + ": " + Arrays.toString(entry.getValue()))
                .collect(Collectors.joining(" | "));

        LOG.info("Endpoint requisitada: ["+request.getMethod().toUpperCase()+"] -> "+request.getRequestURL().toString());
        if (params.length() > 0) LOG.info("Parametros da requisição: "+ params);
    }

    public void request(HttpServletRequest request, Record dados){
        String params = request.getParameterMap().entrySet().stream()
                .map(entry -> entry.getKey() + ": " + Arrays.toString(entry.getValue()))
                .collect(Collectors.joining(" | "));

        LOG.info("Endpoint requisitada: ["+request.getMethod().toUpperCase()+"] "+request.getRequestURL().toString());
        if (params.length() > 0) LOG.info("Parametros da requisição recebida: "+ params);

        LOG.info("JSON recebido para a requisição: "+dados.toString());
    }

    public void request(HttpServletRequest request, Object dados){
        String params = request.getParameterMap().entrySet().stream()
                .map(entry -> entry.getKey() + ": " + Arrays.toString(entry.getValue()))
                .collect(Collectors.joining(" | "));

        LOG.info("Endpoint requisitada: ["+request.getMethod().toUpperCase()+"] "+request.getRequestURL().toString());
        if (params.length() > 0) LOG.info("Parametros da requisição recebida: "+ params);

        LOG.info("JSON recebido para a requisição: " + dados.toString());
    }

    public void info(String info){
        LOG.info(info);
    }
}
