package br.gov.sp.fatec.springbootapp.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.gov.sp.fatec.springbootapp.entity.Personagem;
import br.gov.sp.fatec.springbootapp.service.SegurancaService;

@RestController
@CrossOrigin
@RequestMapping(value = "/personagem")
public class PersonagemController {
    
    @Autowired
    private SegurancaService segService;

    @JsonView(JsonConfig.Personagem.class)
    @GetMapping
    public List<Personagem> buscarTodos() {
        return segService.buscarTodosPersonagens();
    }

    @JsonView(JsonConfig.Personagem.class)
    @GetMapping(value = "/{id}")
    public Personagem buscaPorId(@PathVariable("id") Long id) {
        return segService.buscarPersonagemPorId(id);
    }

    @JsonView(JsonConfig.Personagem.class)
    @PutMapping(value = "/{id}")
    public Personagem atualizarNome(@PathVariable("id") Long id, @RequestBody Personagem personagem) {
        segService.atualizarNomePersonagem(id, personagem.getNome());
        return personagem; 
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletarPersonagem(@PathVariable("id") Long id) {
        segService.deletarPersonagem(id);
        return ResponseEntity.ok("Personagem deletado. Uma pena :(");
    }

    @JsonView(JsonConfig.Personagem.class)
    @PostMapping
    public ResponseEntity<Personagem> cadastrarPersonagem(@RequestBody Personagem personagem,
        UriComponentsBuilder uriComponentsBuilder) {

        personagem = segService.cadastrarPersonagem(personagem.getProprietario().getNomeUsuario(), personagem.getClasse().getNome(), personagem.getNome());
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponentsBuilder.path(
            "/personagem/" + personagem.getId()
        ).build().toUri());
        return new ResponseEntity<Personagem>(personagem, responseHeaders, HttpStatus.CREATED);
    }    
}