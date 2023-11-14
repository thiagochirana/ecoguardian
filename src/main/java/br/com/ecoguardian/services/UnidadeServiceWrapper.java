package br.com.ecoguardian.services;

import br.com.ecoguardian.models.Unidade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class UnidadeServiceWrapper {
    private Unidade unidadeLogada;
}
