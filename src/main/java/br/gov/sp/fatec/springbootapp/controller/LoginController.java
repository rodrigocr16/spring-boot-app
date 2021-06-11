package br.gov.sp.fatec.springbootapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.security.JwtUtils;
import br.gov.sp.fatec.springbootapp.security.Login;
import br.gov.sp.fatec.springbootapp.service.SegurancaService;

@RestController
@RequestMapping(value = "/login")
@CrossOrigin
public class LoginController {
    
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private SegurancaService segService;
    
    @PostMapping
    public Login autenticar(@RequestBody Login login) throws JsonProcessingException {
        Authentication auth = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        auth = authManager.authenticate(auth);
        login.setPassword(null);
        login.setToken(JwtUtils.generateToken(auth));

        String autorizacao = new String();
        Usuario usuario = segService.buscarUsuarioPorNomeUsuario(login.getUsername());
        autorizacao = usuario.getAutorizacoes().iterator().next().getTipo().toString();
        login.setAutorizacao(autorizacao);
        return login;
    }
}
