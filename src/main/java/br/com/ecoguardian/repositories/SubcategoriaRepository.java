package br.com.ecoguardian.repositories;

import br.com.ecoguardian.models.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {
}
