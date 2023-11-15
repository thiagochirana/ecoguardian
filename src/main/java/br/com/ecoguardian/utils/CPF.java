package br.com.ecoguardian.utils;

public class CPF {

    public static boolean validarCPF(String CPF){
        String cpfTratado = CPF.replaceAll("[^0-9]", "");
        return cpfTratado.length() == 11;
    }

    public static String retirarMascara(String CPF){
        return CPF.replaceAll("[^0-9]", "");
    }
}
