package br.com.ecoguardian.repositories;

import br.com.ecoguardian.models.Unidade;
import br.com.ecoguardian.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    @Query("SELECT u.unidadePertencente FROM Usuario u WHERE u = :usuario")
    Optional<Unidade> getUnidadeByUsuario(Usuario usuario);
}
