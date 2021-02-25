package br.gov.sp.fatec.springbootapp.service;


import java.util.HashSet;
import org.springframework.stereotype.Service;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;
import br.gov.sp.fatec.springbootapp.repository.AutorizacaoRepository;

@Service("segurancaService")
public class SegurancaServiceImpl implements SegurancaService {

    @Autowired
    private UsuarioRepository usuRepo;

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
            usuario.setSenha(senha);
            usuario.setNomeExibicao(nomeExibicao);
            usuario.setAutorizacoes(new HashSet<Autorizacao>());
            usuario.getAutorizacoes().add(aut);
            usuRepo.save(usuario);
        return usuario;
    }
    
}