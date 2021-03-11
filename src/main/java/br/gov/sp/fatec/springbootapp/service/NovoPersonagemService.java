package br.gov.sp.fatec.springbootapp.service;

import br.gov.sp.fatec.springbootapp.entity.Personagem;

public interface NovoPersonagemService {
    
    public Personagem novoPersonagem(String proprietario, String classe, String nome);
    
}
