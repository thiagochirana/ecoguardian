package br.com.ecoguardian.models.records;


public record DenunciaRespJSON(
        Long id,
        Boolean sigilo,
        String protocolo,
        String titulo,
        String descricao,
        String dataHoraAbertura,
        Boolean precisaIniciar,
        String nomeMunicipio,
        String nomeEstado,
        String nomeStatusDenuncia,
        String nomeDenunciante
) {
}
