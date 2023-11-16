package br.com.ecoguardian.models.enums;

public enum StatusDenuncia {
    ABERTA("Aberta"),
    FECHADA("Fechada"),
    BLOQUEADA("Bloqueada"),
    EM_ANALISE("Em Análise"),
    RESOLVIDA("Resolvida");

    private final String nome;

    StatusDenuncia(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }
}
