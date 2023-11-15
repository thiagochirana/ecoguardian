package br.com.ecoguardian.models.enums;

public enum TipoPerfil {
    ADMIN("Administrador"),
    DENUNCIANTE("Denunciante"),
    ANALISTA("Analista"),
    ANONIMO("Anônimo");

    private final String valor;

    TipoPerfil(String valor) {
        this.valor = valor;
    }

    public String get() {
        return valor;
    }
}
