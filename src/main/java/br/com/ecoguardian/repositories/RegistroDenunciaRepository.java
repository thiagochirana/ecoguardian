package br.com.ecoguardian.repositories;

import br.com.ecoguardian.models.Denuncia;
import br.com.ecoguardian.models.RegistroDenuncia;
import br.com.ecoguardian.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RegistroDenunciaRepository extends JpaRepository<RegistroDenuncia, Long> {

    @Query("FROM RegistroDenuncia rd WHERE rd.denuncia = :denuncia")
    Optional<List<RegistroDenuncia>> daDenuncia(Denuncia denuncia);

    @Query("FROM RegistroDenuncia WHERE denuncia.denunciante = :usuario")
    Optional<List<RegistroDenuncia>> doUsuario(Usuario usuario);

    @Query("FROM RegistroDenuncia rd WHERE rd.quemAtualizou = :analista")
    Optional<Set<RegistroDenuncia>> atualizadasPeloAnalista(Usuario analista);

    @Query("FROM RegistroDenuncia rd WHERE rd.denuncia = :denuncia AND rd.statusAtual <> 'ABERTA' OR rd.statusAtual <> 'AGUARDANDO_ANALISE' ")
    Optional<List<RegistroDenuncia>> jaEstaEmAnalise(Denuncia denuncia);

}
