package br.gov.sp.fatec.springbootapp.entity;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.AttributeOverride;

@Entity
@Table(name = "cla_classe")
@AttributeOverride(name = "id", column = @Column(name = "cla_id"))
public class Classe extends GeraId{
    
    @Column(name = "cla_nome")
    private String nome;


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}