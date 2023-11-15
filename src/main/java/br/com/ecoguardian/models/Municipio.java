package br.com.ecoguardian.models;

import br.com.ecoguardian.models.enums.Estado;
import jakarta.persistence.*;

@Entity
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

}
