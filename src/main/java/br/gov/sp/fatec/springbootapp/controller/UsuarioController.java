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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.service.SegurancaService;

@RestController
@CrossOrigin
@RequestMapping(value = "/usuario")
public class UsuarioController {
    
    @Autowired
    private SegurancaService segService;

    @JsonView(JsonConfig.Autorizacao.class)
    @GetMapping(value = "/auth/{autorizacao}")
    public Autorizacao buscarAutorizacaoPorTipo(@PathVariable("autorizacao") String tipo) {
        return segService.buscarAutorizacaoPorTipo(tipo);
    }

    @JsonView(JsonConfig.Usuario.class)
    @GetMapping
    public List<Usuario> buscarTodos() {
        return segService.buscarTodosUsuarios();
    }

    @JsonView(JsonConfig.Usuario.class)
    @GetMapping(value = "/{id}")
    public Usuario buscaPorId(@PathVariable("id") Long id) {
        return segService.buscarUsuarioPorId(id);
    }

    @JsonView(JsonConfig.Usuario.class)
    @GetMapping(value = "/nomeUsuario")
    public Usuario buscaPorNomeUsuario(@RequestParam(value="nomeUsuario") String nomeUsuario) {
        return segService.buscarUsuarioPorNomeUsuario(nomeUsuario);
    }

    @JsonView(JsonConfig.Usuario.class)
    @PutMapping(value = "/{id}")
    public Usuario atualizarNomeExibicao(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
        segService.atualizarNomeExibicao(id, usuario.getNomeExibicao());
        return usuario; 
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable("id") Long id) {
        segService.deletarUsuario(id);
        return ResponseEntity.ok("Usuário removido");
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario,
        UriComponentsBuilder uriComponentsBuilder) {

        usuario = segService.criarUsuario(usuario.getNomeUsuario(), usuario.getSenha(), usuario.getNomeExibicao(), "usuario");
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponentsBuilder.path(
            "/usuario/" + usuario.getId()
        ).build().toUri());
        return new ResponseEntity<Usuario>(usuario, responseHeaders, HttpStatus.CREATED);
    }    
}
