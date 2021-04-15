package br.gov.sp.fatec.springbootapp.entity;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.springbootapp.controller.JsonConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.AttributeOverride;

@Entity
@Table(name = "usu_usuario")
@AttributeOverride(name = "id", column = @Column(name = "usu_id"))
public class Usuario extends GeraId{

    @JsonView({JsonConfig.Usuario.class, JsonConfig.Autorizacao.class, JsonConfig.Personagem.class})
    @Column(name = "usu_nome_usuario")
    private String nomeUsuario;

    @Column(name = "usu_senha")
    private String senha;

    @JsonView(JsonConfig.Usuario.class)
    @Column(name = "usu_nome_exibicao")
    private String nomeExibicao;

    @JsonView(JsonConfig.Usuario.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "uau_usuario_autorizacao",
        joinColumns = { @JoinColumn(name = "uau_usu_id") },
        inverseJoinColumns = { @JoinColumn(name = "uau_aut_id") }
    )
    private Set<Autorizacao> autorizacoes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "proprietario")
    private Set<Personagem> personagens;    


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