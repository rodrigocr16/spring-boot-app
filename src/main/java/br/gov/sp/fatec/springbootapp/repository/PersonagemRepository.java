package br.gov.sp.fatec.springbootapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.gov.sp.fatec.springbootapp.entity.Personagem;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
    
}