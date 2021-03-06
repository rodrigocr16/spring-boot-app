package br.gov.sp.fatec.springbootapp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.gov.sp.fatec.springbootapp.entity.Usuario;
import org.springframework.test.annotation.Rollback;
import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;
import br.gov.sp.fatec.springbootapp.service.SegurancaService;
import br.gov.sp.fatec.springbootapp.repository.AutorizacaoRepository;

@SpringBootTest
@Transactional
@Rollback
class SpringBootAppApplicationTests {

    @Autowired
    private UsuarioRepository usuarioRepo;
    
    @Autowired
    private AutorizacaoRepository autRepo;

    @Autowired
    private SegurancaService segService;

    // TESTES PRINCIPAIS
	@Test
	void contextLoads() {
    }
    @Test
    void testaInsercao() {
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario("usuario_teste");
        usuario.setSenha("senha");
        usuario.setAutorizacoes(new HashSet<Autorizacao>());
            Autorizacao aut = new Autorizacao();
            aut.setTipo("USUARIO");
            autRepo.save(aut);
            usuario.getAutorizacoes().add(aut);
        usuario.setNomeExibicao("teste");

        usuarioRepo.save(usuario);
        assertNotNull(usuario.getId());
    }
    @Test
    void testaAutorizacao() {
        Usuario usuario = usuarioRepo.findById(1L).get();
        assertEquals("admin", usuario.getAutorizacoes().iterator().next().getTipo());
    }
    @Test
    void testaUsuario() {        
        Autorizacao aut = autRepo.findById(1L).get();
        assertEquals("rodrigocr16", aut.getUsuarios().iterator().next().getNomeUsuario());
    }

    // TESTES DE BUSCA
    @Test
    void testaBuscaUsuarioNomeContains() {
        List<Usuario> usuarios = usuarioRepo.findUsuarioByNomeExibicaoContainsIgnoreCase("RoDrIgO");
        assertTrue(!usuarios.isEmpty());
    }
    @Test
    void testaBuscaUsuarioNomeSenha() {
        Usuario usuario = usuarioRepo.findUsuarioByNomeUsuarioAndSenha("rodrigocr16", "pepino");
        assertNotNull(usuario);
    }
    @Test
    void testaBuscaUsuarioAutorizacao() {
        List<Usuario> usuarios = usuarioRepo.findUsuarioByAutorizacoesTipo("admin");
        assertTrue(!usuarios.isEmpty());
    }

    // TESTES DE BUSCA COM @QUERY
    @Test
    void testaQueryBuscaUsuarioNome() {
        Usuario usuario = usuarioRepo.buscaPorNome("rodrigo reis");
        assertNotNull(usuario);
    }
    @Test
    void testaQueryBuscaUsuarioNomeSenha() {
        Usuario usuario = usuarioRepo.buscaPorNomeESenha("rodrigocr16", "pepino");
        assertNotNull(usuario);
    }
    @Test
    void testaQueryBuscaUsuarioAutorizacao() {
        List<Usuario> usuarios = usuarioRepo.buscaPorNomeAutorizacao("admin");
        assertTrue(!usuarios.isEmpty());
    }

    // TESTE DE SERVICES
    @Test
    void testaServicoCriaUsuario() {
        Usuario usuario = segService.criarUsuario("joedoe", "j03m4m4", "joseph doestar", "usuario");
        assertNotNull(usuario);
    }
}
