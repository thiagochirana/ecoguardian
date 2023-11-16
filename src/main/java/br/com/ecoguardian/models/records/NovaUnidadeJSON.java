package br.com.ecoguardian.models.records;

import br.com.ecoguardian.models.Municipio;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.enums.Estado;

public record NovaUnidadeJSON(
        String nome,

        Estado estado,

        String idIBGE,

        Usuario usuarioVinculado,

        String logradouro,

        String bairro,

        String pontoDeReferencia


) {
}
