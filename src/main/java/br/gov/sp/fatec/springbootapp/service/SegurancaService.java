package br.gov.sp.fatec.springbootapp.service;

import java.util.List;

import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.entity.Usuario;

public interface SegurancaService {
    
    public Usuario criarUsuario(String nomeUsuario, String senha, String nomeExibicao, String autorizacao);
    
    public List<Usuario> buscarTodosUsuarios();

    public Usuario buscarUsuarioPorId(Long id);

    public Usuario buscarUsuarioPorNomeUsuario(String nomeUsuario);

    public Autorizacao buscarAutorizacaoPorTipo(String tipo);

    public Usuario atualizarNomeExibicao(Long id, String nomeExibicao);
}