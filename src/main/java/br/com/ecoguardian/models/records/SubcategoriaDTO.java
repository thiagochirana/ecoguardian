package br.com.ecoguardian.models.records;

public record SubcategoriaDTO(
        Long idSubcategoria,
        String descricao,

        Long idCategoria
) {
}
