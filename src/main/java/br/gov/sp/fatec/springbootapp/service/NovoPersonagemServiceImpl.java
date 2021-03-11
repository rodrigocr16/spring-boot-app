package br.gov.sp.fatec.springbootapp.service;

import javax.transaction.Transactional;

import br.gov.sp.fatec.springbootapp.entity.Classe;
import br.gov.sp.fatec.springbootapp.entity.Personagem;
import br.gov.sp.fatec.springbootapp.entity.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import br.gov.sp.fatec.springbootapp.repository.ClasseRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;
import br.gov.sp.fatec.springbootapp.repository.PersonagemRepository;

public class NovoPersonagemServiceImpl implements NovoPersonagemService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private ClasseRepository classeRepo;

    @Autowired
    private PersonagemRepository personagemRepo;

    @Autowired
    private SegurancaService segService;

    @Override
    @Transactional
    public Personagem novoPersonagem(String proprietario, String classe, String nome) {
        
        Usuario usu = usuarioRepo.findUsuarioByNomeUsuario(proprietario);
            if(usu == null) {
                usu = segService.criarUsuario(proprietario, "senha_padrao", "convidado", "usuario");
            }
        Classe cla = classeRepo.findByNome(classe);
            if(cla == null) {
                cla = new Classe();
                cla.setNome(classe);
                classeRepo.save(cla);
            }
        Personagem personagem = new Personagem();
            personagem.setProprietario(usu);
            personagem.setClasse(cla);
            personagem.setNome(nome);
            personagemRepo.save(personagem);
            
        return personagem;

    }

}
