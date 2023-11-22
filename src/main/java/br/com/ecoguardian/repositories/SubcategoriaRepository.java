package br.com.ecoguardian.repositories;

import br.com.ecoguardian.models.Categoria;
import br.com.ecoguardian.models.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {

    @Query("FROM Subcategoria s WHERE s.categoria=:categoria")
    Optional<List<Subcategoria>> daCategoria(Categoria categoria);
}
