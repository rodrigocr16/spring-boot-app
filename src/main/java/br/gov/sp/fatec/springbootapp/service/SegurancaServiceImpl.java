package br.gov.sp.fatec.springbootapp.service;

import java.util.List;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.springbootapp.entity.Classe;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.entity.Personagem;
import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.repository.ClasseRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;
import br.gov.sp.fatec.springbootapp.repository.PersonagemRepository;
import br.gov.sp.fatec.springbootapp.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springbootapp.exception.RegistroNaoEncontradoException;

@Service("segurancaService")
public class SegurancaServiceImpl implements SegurancaService {

    @Autowired
    private ClasseRepository claRepo;    
    
    @Autowired
    private UsuarioRepository usuRepo;

    @Autowired
    private PasswordEncoder passEncoder;

    @Autowired
    private PersonagemRepository perRepo;

    @Autowired
    private AutorizacaoRepository autRepo;
    
    @Override
    @Transactional
    public Usuario criarUsuario(String nomeUsuario, String senha, String nomeExibicao, String autorizacao) {
        Autorizacao aut = autRepo.findByTipo(autorizacao);
            if(aut == null){
                aut = new Autorizacao();
                aut.setTipo(autorizacao);
                autRepo.save(aut);
            }
        Usuario usuario = new Usuario();
            usuario.setNomeUsuario(nomeUsuario);
            usuario.setSenha(passEncoder.encode(senha));
            usuario.setNomeExibicao(nomeExibicao);
            usuario.setAutorizacoes(new HashSet<Autorizacao>());
            usuario.getAutorizacoes().add(aut);
            usuRepo.save(usuario);
        return usuario;
    }
    
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> buscarTodosUsuarios() {
        return usuRepo.findAll();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Usuario buscarUsuarioPorId(Long id) {
        Optional<Usuario> usuarioOp = usuRepo.findById(id);
        if(usuarioOp.isPresent()) {
            return usuarioOp.get();
        } else {
            throw new RegistroNaoEncontradoException("Usuario não encontrado");
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Usuario buscarUsuarioPorNomeUsuario(String nomeUsuario) {
        Usuario usuario = usuRepo.findUsuarioByNomeUsuario(nomeUsuario);
        if(usuario != null) {
            return usuario;
        } else {
            throw new RegistroNaoEncontradoException("Usuario não encontrado");
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Autorizacao buscarAutorizacaoPorTipo(String tipo) {
        Autorizacao autorizacao = autRepo.findByTipo(tipo);
        if(autorizacao != null){
            return autorizacao;
        } else {
            throw new RegistroNaoEncontradoException("Autorizacao não encontrada");
        }
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Usuario atualizarNomeExibicao(Long id, String nomeExibicao) {
        Usuario usuario = buscarUsuarioPorId(id);
        if(usuario != null){
            usuario.setNomeExibicao(nomeExibicao);
            usuRepo.save(usuario);
            return usuario;
        } else {
            throw new RegistroNaoEncontradoException("Usuário não encontrado");
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void deletarUsuario(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        if(usuario != null){
            usuRepo.delete(usuario);
        } else {
            throw new RegistroNaoEncontradoException("Usuário não encontrado");
        }
    }

    /// SEGMENTO NOVO - PERSONAGENS

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Personagem> buscarTodosPersonagens() {
        return perRepo.findAll();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Personagem buscarPersonagemPorId(Long id) {
        Optional<Personagem> personagemOp = perRepo.findById(id);
        if(personagemOp.isPresent()) {
            return personagemOp.get();
        } else {
            throw new RegistroNaoEncontradoException("Personagem não encontrado");
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Personagem atualizarNomePersonagem(Long id, String nome) {
        Personagem personagem = buscarPersonagemPorId(id);
        if(personagem != null){
            personagem.setNome(nome);
            perRepo.save(personagem);
            return personagem;
        } else {
            throw new RegistroNaoEncontradoException("Personagem não encontrado");
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void deletarPersonagem(Long id) {
        Personagem personagem = buscarPersonagemPorId(id);
        if(personagem != null){
            perRepo.delete(personagem);
        } else {
            throw new RegistroNaoEncontradoException("P6ersonagem não encontrado");
        }
    }

    @Override
    @Transactional
    public Personagem cadastrarPersonagem(String proprietario, String classe, String nome) {
        
        Usuario usu = usuRepo.findUsuarioByNomeUsuario(proprietario);
            if(usu == null) {
                usu = new Usuario();
                usu = criarUsuario(proprietario, passEncoder.encode("senhaPadrao"), "convidado", "ROLE_USER");
            }
        Classe cla = claRepo.findClasseByNome(classe);
            if(cla == null) {
                cla = new Classe();
                cla.setNome(classe);
                claRepo.save(cla);
            }
        Personagem personagem = new Personagem();
            personagem.setProprietario(usu);
            personagem.setClasse(cla);
            personagem.setNome(nome);
            perRepo.save(personagem);
            
        return personagem;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuRepo.findUsuarioByNomeUsuario(username);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario nao encontrado!");
        }
        return User.builder().username(username).password(usuario.getSenha())
            .authorities(usuario.getAutorizacoes().stream()
                .map(Autorizacao::getTipo).collect(Collectors.toList())
                .toArray(new String[usuario.getAutorizacoes().size()]))
            .build();
    }
}