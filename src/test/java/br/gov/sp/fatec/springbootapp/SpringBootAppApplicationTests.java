package br.gov.sp.fatec.springbootapp;

import java.util.List;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import br.gov.sp.fatec.springbootapp.entity.Classe;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.entity.Personagem;
import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.service.SegurancaService;
import br.gov.sp.fatec.springbootapp.service.NovoPersonagemService;
import br.gov.sp.fatec.springbootapp.repository.ClasseRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;
import br.gov.sp.fatec.springbootapp.repository.PersonagemRepository;
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
    private PersonagemRepository personagemRepo;

    @Autowired
    private ClasseRepository classeRepo;

    @Autowired
    private SegurancaService segService;

    @Autowired
    private NovoPersonagemService perService;

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
            aut.setTipo("ROLE_USER");
            autRepo.save(aut);
            usuario.getAutorizacoes().add(aut);
        usuario.setNomeExibicao("teste");

        usuarioRepo.save(usuario);
        assertNotNull(usuario.getId());
    }
    @Test
    void testaClasse() {
        Classe classe = new Classe();
        classe.setNome("Barbaro");
        classeRepo.save(classe);
        assertNotNull(classe.getId());
    }
    @Test
    void testaAutorizacao() {
        Usuario usuario = usuarioRepo.findById(1L).get();
        assertEquals("ROLE_ADMIN", usuario.getAutorizacoes().iterator().next().getTipo());
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
        List<Usuario> usuarios = usuarioRepo.findUsuarioByAutorizacoesTipo("ROLE_ADMIN");
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
        List<Usuario> usuarios = usuarioRepo.buscaPorNomeAutorizacao("ROLE_ADMIN");
        assertTrue(!usuarios.isEmpty());
    }

    // TESTES DE PERSONAGEM
    @Test
    void testaPersonagem() {
        Personagem personagem = new Personagem();
            personagem.setProprietario(usuarioRepo.findUsuarioByNomeUsuario("rodrigocr16"));
            personagem.setClasse(classeRepo.findClasseByNome("bardo"));
            personagem.setNome("seeker");
        personagemRepo.save(personagem);
        assertNotNull(personagem.getId());
    }
    @Test
    void testaListarPersonagens() {
        List<Personagem> personagens = personagemRepo.buscaPorProprietario("rodrigocr16");
        assertTrue(!personagens.isEmpty());
    }
    @Test
    void testaListarPersonagensClasse() {
        List<Personagem> personagens = personagemRepo.buscaPorClasseEProprietario("bardo", "rodrigocr16");
        assertTrue(!personagens.isEmpty());
    }

    // TESTE DE SERVICES
    @Test
    void testaServicoCriaUsuario() {
        Usuario usuario = segService.criarUsuario("joedoe", "j03m4m4", "joseph doestar", "ROLE_USER");
        assertNotNull(usuario);
    }

    @Test
    void testaServicoCriaPersonagem() {
        Personagem personagem = perService.novoPersonagem("usuario_teste_2", "paladino", "artimus");
        assertNotNull(personagem);
    }

    // TESTES DE CONTROLLER

    @Test
    void atualizaUsuario() {
        segService.atualizarNomeExibicao(1l, "rodrigo atualizado");
        
        Usuario usuario = segService.buscarUsuarioPorId(1l);
        assertEquals(usuario.getNomeExibicao(), "rodrigo atualizado");
    }
}