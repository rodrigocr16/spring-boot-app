package br.gov.sp.fatec.springbootapp.entity;

import java.util.Set;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;


import javax.persistence.AttributeOverride;

@Entity
@Table(name = "aut_autorizacao")
@AttributeOverride(name = "id", column = @Column(name = "aut_id"))
public class Autorizacao extends GeraId{

    @Column(name = "aut_tipo")
    private String tipo;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "autorizacoes")
    private Set<Usuario> usuarios;


    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Set<Usuario> getUsuarios(){
        return this.usuarios;
    }
    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}