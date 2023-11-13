package br.com.ecoguardian.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Datas {

    public static Date agora(){
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        ZoneId zonaDefault = ZoneId.systemDefault();
        return Date.from(dataHoraAtual.atZone(zonaDefault).toInstant());
    }
}
