package br.com.ecoguardian.repositories;

import br.com.ecoguardian.models.Denuncia;
import br.com.ecoguardian.models.Municipio;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.enums.Estado;
import br.com.ecoguardian.models.enums.StatusDenuncia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {

    @Query("FROM Denuncia WHERE denunciante = :usuario")
    Optional<List<Denuncia>> listarTodosDoUsuario(Usuario usuario);

    @Query("FROM Denuncia d WHERE d.statusDenuncia = 'ABERTO' and d.denunciante = :usuario")
    Optional<List<Denuncia>> listarTodasEmAbertoDoUsuario(Usuario usuario);

    @Query("FROM Denuncia d WHERE d.statusDenuncia = :status and d.denunciante = :usuario")
    Optional<List<Denuncia>> listarTodasDoUsuarioComStatus(Usuario usuario, StatusDenuncia status);

    @Query("FROM Denuncia d WHERE d.localizacao in (FROM Localizacao l WHERE l.municipio in (FROM Municipio m WHERE m.estado = :estado))")
    Optional<List<Denuncia>> doEstado(Estado estado);

    @Query("FROM Denuncia d WHERE d.localizacao in (FROM Localizacao l WHERE l.municipio = :municipio)")
    Optional<List<Denuncia>> doMunicipio(Municipio municipio);
}
