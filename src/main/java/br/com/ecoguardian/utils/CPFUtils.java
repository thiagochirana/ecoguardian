package br.com.ecoguardian.utils;

public class CPFUtils {

    public static boolean validarCPF(String CPF){
        String cpfTratado = CPF.replaceAll("[^0-9]", "");
        return cpfTratado.length() == 11;
    }

    public static String retirarMascara(String CPF){
        return CPF.replaceAll("[^0-9]", "");
    }

    public static String inserirMascara(String CPF){
        CPF = CPF.replaceAll("[^0-9]", "");
        return CPF.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}
