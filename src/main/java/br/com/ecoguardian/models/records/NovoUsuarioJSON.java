package br.com.ecoguardian.models.records;

import br.com.ecoguardian.models.enums.TipoPerfil;

public record NovoUsuarioJSON(
        String nome,

        String cpf,

        String senha,

        String email,

        String telefone,

        TipoPerfil tipoPerfil
) {
}
