package br.com.ecoguardian.models.enums;

public enum TipoPerfil {
    ECO_GUARDIAN("EcoGuardian"),
    ADMIN("Administrador"),
    DENUNCIANTE("Denunciante"),
    ANALISTA("Analista"),
    ANONIMO("Anônimo");

    private final String valor;

    TipoPerfil(String valor) {
        this.valor = valor;
    }

    public String getNome() {
        return valor;
    }
}
