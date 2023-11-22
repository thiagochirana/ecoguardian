package br.com.ecoguardian.models;

import br.com.ecoguardian.models.records.SubcategoriaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Subcategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne(cascade = CascadeType.ALL)
    private Categoria categoria;

    public void setId(Long id) {
        this.id = id;
    }

    public Subcategoria() {
    }

    public Subcategoria(String descricao, Categoria categoria){
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public SubcategoriaDTO getDTO(){
        return new SubcategoriaDTO(this.id, this.descricao, this.categoria.getId());
    }

}
