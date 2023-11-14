package br.com.ecoguardian.utils;

public class CPF {

    public static boolean validarCPF(String CPF){
        return CPF.replaceAll("[^0-9]", "").length() == 11;
    }

    public static String retirarMascara(String CPF){
        return CPF.replaceAll("[^0-9]", "");
    }
}
