package br.gov.sp.fatec.springbootapp.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.service.SegurancaService;

@CrossOrigin
@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
    
    @Autowired
    private SegurancaService segService;

    @GetMapping
    public List<Usuario> buscarTodos() {
        return segService.buscarTodosUsuarios();
    }

    @GetMapping(value = "/{id}")
    public Usuario buscaPorId(@PathVariable("id") Long id) {
        return segService.buscarUsuarioPorId(id);
    }

    @GetMapping(value = "/nomeUsuario")
    public Usuario buscaPorNomeUsuario(@RequestParam(value="nomeUsuario") String nomeUsuario) {
        return segService.buscarUsuarioPorNomeUsuario(nomeUsuario);
    }

    @PostMapping
    public Usuario CadastrarUsuario(@RequestBody Usuario usuario) {
        return segService.criarUsuario(usuario.getNomeUsuario(), usuario.getSenha(), usuario.getNomeExibicao(), "usuario");
    }
}
