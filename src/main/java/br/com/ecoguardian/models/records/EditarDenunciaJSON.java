package br.com.ecoguardian.models.records;

public record EditarDenunciaJSON(
        Long denunciaId,
        Long categoriaId,
        Long subcategoriaId
) {
}
