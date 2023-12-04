package br.com.ecoguardian.models.enums;

public enum StatusDenuncia {
    ABERTA("Aberta"),
    AGUARDANDO_ANALISE("Aguardando Análise"),
    ANALISE_INICIADA("Análise Iniciada"),
    EM_ANALISE("Em Análise"),
    RESOLVIDA("Resolvida"),
    REJEITADA("Rejeitada"),
    ENCERRADA_PELO_DENUNCIANTE("Encerrada pelo Denunciante"),
    FECHADA("Fechada");

    private final String nome;

    StatusDenuncia(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }
}
