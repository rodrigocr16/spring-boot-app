package br.gov.sp.fatec.springbootapp.entity;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.AttributeOverride;
import com.fasterxml.jackson.annotation.JsonView;
import br.gov.sp.fatec.springbootapp.controller.JsonConfig;

@JsonView(JsonConfig.Personagem.class)
@Entity
@Table(name = "per_personagem")
@AttributeOverride(name = "id", column = @Column(name = "per_id"))
public class Personagem extends GeraId{
    
    @JsonView(JsonConfig.Personagem.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "per_proprietario")
    private Usuario proprietario;

    @JsonView(JsonConfig.Personagem.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "per_classe")
    private Classe classe;

    @JsonView(JsonConfig.Personagem.class)
    @Column(name = "per_nome")
    private String nome;


    public Usuario getProprietario() {
        return proprietario;
    }
    public void setProprietario(Usuario proprietario){
        this.proprietario = proprietario;
    }

    public Classe getClasse() {
        return classe;
    }
    public void setClasse(Classe classe){
        this.classe = classe;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
}