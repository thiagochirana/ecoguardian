package br.com.ecoguardian.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Datas {

    public static Date agora(){
        LocalDateTime dataHoraAtual = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        ZoneId zonaDefault = ZoneId.systemDefault();
        return Date.from(dataHoraAtual.atZone(zonaDefault).toInstant());
    }

    public static String dataFormatada(Date data){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy '-' HH:mm:ss");
        return formato.format(data);
    }

    public static int getAnoAtual(){
        return agora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
    }

    public static Date emStringParaDate(String data){
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            return formato.parse(data);
        } catch (ParseException e){
            return agora();
        }
    }
}
