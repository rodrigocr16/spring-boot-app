package br.gov.sp.fatec.springbootapp.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.entity.Personagem;
import br.gov.sp.fatec.springbootapp.entity.Usuario;

public interface SegurancaService extends UserDetailsService {
    
    public Usuario criarUsuario(String nomeUsuario, String senha, String nomeExibicao, String autorizacao);
    
    public List<Usuario> buscarTodosUsuarios();

    public Usuario buscarUsuarioPorId(Long id);

    public Usuario buscarUsuarioPorNomeUsuario(String nomeUsuario);

    public Autorizacao buscarAutorizacaoPorTipo(String tipo);

    public Usuario atualizarNomeExibicao(Long id, String nomeExibicao);

    public void deletarUsuario(Long id);


    public List<Personagem> buscarTodosPersonagens();

    public Personagem buscarPersonagemPorId(Long id);

    public Personagem atualizarNomePersonagem(Long id, String nome);

    public void deletarPersonagem(Long id);

    public Personagem cadastrarPersonagem(String proprietario, String classe, String nome);
}