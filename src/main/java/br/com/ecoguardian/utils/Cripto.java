package br.com.ecoguardian.utils;

import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.records.SenhaCriptoDTO;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Cripto {

    public static SenhaCriptoDTO criptografar(String texto) {
        long start = System.nanoTime();

        byte[] bytes = texto.getBytes();
        String textoCriptografado = Base64.getEncoder().encodeToString(bytes);

        long end = System.nanoTime();
        long tempoExec = end - start;

        return new SenhaCriptoDTO(textoCriptografado, tempoExec);
    }

    public static SenhaCriptoDTO descriptografar(String textoCriptografado) {
        long start = System.nanoTime();

        byte[] bytesDescriptografados = Base64.getDecoder().decode(textoCriptografado);
        String textoDescriptografado = new String(bytesDescriptografados);

        long end = System.nanoTime();
        Long tempoExec = end - start;

        return new SenhaCriptoDTO(textoDescriptografado, tempoExec);
    }

    public static String conversorTempoParaNano(long tempoExecutado){
        long tempoEmMilissegundos = 0L;
        long tempoEmMicrossegundos = 0L;
        String tempoTotal = "";
        if (tempoExecutado > 1000000L){
            tempoEmMilissegundos = tempoExecutado / 1000000L % 1000L;
            tempoEmMicrossegundos = tempoExecutado / 1000L % 1000L;
            tempoTotal = tempoEmMilissegundos+" ms, "+tempoEmMicrossegundos+ "µs, ";
        } else if ( tempoExecutado > 1000L) {
            tempoEmMicrossegundos = tempoExecutado / 1000L % 1000L ;
            tempoTotal = tempoEmMicrossegundos+ "µs, ";
        }

        long tempoEmNanossegundos = tempoExecutado % 1000L;
        tempoTotal += tempoEmNanossegundos + " ns";
        return tempoTotal;
    }
}
