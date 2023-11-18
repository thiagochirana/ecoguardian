package br.com.ecoguardian.models.records;

public record MensagemView(
        Boolean notificar,
        Boolean sucesso,
        String titulo,
        String mensagem,
        String outraMsg
) {

}
