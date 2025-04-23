package br.com.dom.inglesadm_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dom.inglesadm_backend.model.User;
import br.com.dom.inglesadm_backend.repository.UserRepository;

@RestController
@RequestMapping("usuarios")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> usuarios = userRepository.findAll();
        return ResponseEntity.ok().body(usuarios);
    }
    
}
