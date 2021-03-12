package br.gov.sp.fatec.springbootapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.gov.sp.fatec.springbootapp.entity.Classe;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
    
    public Classe findClasseByNome(String nome);
}