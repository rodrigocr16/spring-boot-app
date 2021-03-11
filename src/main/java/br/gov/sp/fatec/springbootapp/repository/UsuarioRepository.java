package br.gov.sp.fatec.springbootapp.repository;

import java.util.List;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    public List<Usuario> findUsuarioByNomeExibicaoContainsIgnoreCase(String nomeExibicao);
    
    public List<Usuario> findUsuarioByAutorizacoesTipo(String autorizacao);

    public Usuario findUsuarioByNomeUsuario(String nomeUsuario);
    
    public Usuario findUsuarioByNomeExibicaoOrNomeUsuario(String nomeExibicao, String nomeUsuario);

    public Usuario findUsuarioByNomeUsuarioAndSenha(String nomeUsuario, String senha);

    // ?1 indica que utilizará o primeiro parâmetro
    @Query("select usu from Usuario usu where usu.nomeExibicao = ?1")
    public Usuario buscaPorNome(String nomeExibicao);

    @Query("select usu from Usuario usu where usu.nomeUsuario = ?1 and usu.senha = ?2")
    public Usuario buscaPorNomeESenha(String nomeUsuario, String senha);
    
    @Query("select usu from Usuario usu inner join usu.autorizacoes aut where aut.tipo = ?1")
    public List<Usuario> buscaPorNomeAutorizacao(String autorizacao);
}