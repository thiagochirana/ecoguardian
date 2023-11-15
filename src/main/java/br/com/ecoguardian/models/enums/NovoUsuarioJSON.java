package br.com.ecoguardian.models.enums;

public record NovoUsuarioJSON(
        String nome,

        String cpf,

        String senha,

        TipoPerfil tipoPerfil
) {
}
