package br.com.ecoguardian.repositories;

import br.com.ecoguardian.models.Unidade;
import br.com.ecoguardian.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    @Query("SELECT u.unidadesPertencentes FROM Usuario u WHERE u = :usuario")
    Optional<List<Unidade>> getUnidadesByUsuario(Usuario usuario);
}
