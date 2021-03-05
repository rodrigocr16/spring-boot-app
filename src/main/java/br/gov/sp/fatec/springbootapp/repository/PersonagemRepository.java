package br.gov.sp.fatec.springbootapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import br.gov.sp.fatec.springbootapp.entity.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
    
    @Query("select per from Personagem per inner join per.proprietario usu where usu.nomeUsuario = ?1")
    public List<Personagem> buscaPorProprietario(String nomeUsuario);

    @Query("select per from Personagem per inner join per.classe cla inner join per.proprietario usu where cla.nome = ?1 and usu.nomeUsuario = ?2")
    public List<Personagem> buscaPorClasseEProprietario(String classe, String proprietario);
}