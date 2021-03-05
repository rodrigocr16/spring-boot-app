package br.gov.sp.fatec.springbootapp;

import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import br.gov.sp.fatec.springbootapp.repository.ClasseRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringBootAppApplication {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private ClasseRepository classeRepo;

	public static void main(String[] args) {
        SpringApplication.run(SpringBootAppApplication.class, args);
        
        transacao();

    }
    
    public static void transacao() {
        System.out.print("Pau no cu dos prejudicados");
    }
}
