package br.gov.sp.fatec.springbootapp.repository;

import java.util.List;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    public List<Usuario> findUsuarioByNomeExibicaoContainsIgnoreCase(String nomeExibicao);

    public Usuario findUsuarioByNomeExibicaoOrNomeUsuarioContainsIgnoreCase(String nomeExibicao, String nomeUsuario);

    public List<Usuario> findUsuarioByAutorizacoesTipo(String autorizacao);

    public Usuario findUsuarioByNomeUsuarioAndSenha(String nomeUsuario, String senha);
}