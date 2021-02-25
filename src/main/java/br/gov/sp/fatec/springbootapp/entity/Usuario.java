package br.gov.sp.fatec.springbootapp.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.AttributeOverride;

@Entity
@Table(name = "usu_usuario")
@AttributeOverride(name = "id", column = @Column(name = "usu_id"))
public class Usuario extends GeraId{

    @Column(name = "usu_nome_usuario")
    private String nomeUsuario;

    @Column(name = "usu_senha")
    private String senha;

    @Column(name = "usu_nome_exibicao")
    private String nomeExibicao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "uau_usuario_autorizacao",
        joinColumns = { @JoinColumn(name = "uau_usu_id") },
        inverseJoinColumns = { @JoinColumn(name = "uau_aut_id") }
    )
    private Set<Autorizacao> autorizacoes;

    
    // METHODS

    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }
    public void setNomeExibicao(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public Set<Autorizacao> getAutorizacoes() {
        return this.autorizacoes;
    }
    public void setAutorizacoes(HashSet<Autorizacao> hashSet) {
        this.autorizacoes = hashSet;
    }
}