package br.com.ecoguardian.repositories;

import br.com.ecoguardian.models.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {

    @Query("FROM Municipio m WHERE m.idIBGE = :idIBGE")
    Optional<Municipio> obterByIdIBGE(Long idIBGE);
}
