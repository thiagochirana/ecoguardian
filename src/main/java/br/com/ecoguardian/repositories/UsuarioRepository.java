package br.com.ecoguardian.repositories;

import br.com.ecoguardian.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("FROM Usuario u where u.CPF = :CPF")
    Optional<Usuario> findByCPF(String CPF);

    @Query("SELECT max(u.id) FROM Usuario u")
    Long ultimoId();
}
