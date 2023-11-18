package br.com.ecoguardian.repositories;

import br.com.ecoguardian.models.Criptografia;
import br.com.ecoguardian.models.Usuario;
import br.com.ecoguardian.models.enums.ExecucaoCripto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriptografiaRepository extends JpaRepository<Criptografia, Long> {

    @Query("FROM Criptografia c WHERE c.usuario = :usuario")
    Optional<List<Criptografia>> doUsuario(Usuario usuario);

    @Query("FROM Criptografia c WHERE c.usuario = :usuario AND c.tipoExecucao = :cripto")
    Optional<List<Criptografia>> doTipoDeExecucaoDoUsuario(ExecucaoCripto cripto, Usuario usuario);

    @Query("FROM Criptografia c WHERE c.tipoExecucao = :cripto")
    Optional<List<Criptografia>> doTipoDeExecucaoDeCriptografia(ExecucaoCripto cripto);
}
