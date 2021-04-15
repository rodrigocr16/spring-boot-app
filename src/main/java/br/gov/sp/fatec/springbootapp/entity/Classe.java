package br.gov.sp.fatec.springbootapp.entity;

import java.util.Set;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.AttributeOverride;
import com.fasterxml.jackson.annotation.JsonView;
import br.gov.sp.fatec.springbootapp.controller.JsonConfig;

@Entity
@Table(name = "cla_classe")
@AttributeOverride(name = "id", column = @Column(name = "cla_id"))
public class Classe extends GeraId{
    
    @JsonView(JsonConfig.Personagem.class)
    @Column(name = "cla_nome")
    private String nome;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classe")
    private Set<Personagem> personagens;


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}