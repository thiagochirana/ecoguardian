package br.com.ecoguardian.repositories;

import br.com.ecoguardian.models.Arquivo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

}
