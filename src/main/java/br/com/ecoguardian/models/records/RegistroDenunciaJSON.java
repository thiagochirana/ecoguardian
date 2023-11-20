package br.com.ecoguardian.models.records;

import br.com.ecoguardian.models.enums.StatusDenuncia;

public record RegistroDenunciaJSON(
        Long denunciaId,
        String titulo,
        String descricao,
        Long idUsuario,
        StatusDenuncia statusDenuncia

) {
}
