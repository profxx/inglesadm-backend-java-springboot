package br.com.dom.inglesadm_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dom.inglesadm_backend.model.Curso;
import br.com.dom.inglesadm_backend.service.CursoService;

@RestController
@RequestMapping("cursos")
public class CursoController {
    
    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> findAll(){
        List<Curso> courses = cursoService.findAll();
        return ResponseEntity.ok().body(courses);
    }

    @GetMapping("{id}")
    public ResponseEntity<Curso> findById(@PathVariable Long id){
        Curso curso = cursoService.findById(id);
        return ResponseEntity.ok().body(curso);
    }

    @PostMapping
    public ResponseEntity<Curso> insertNew(@RequestBody Curso curso){
        Curso cursoInserido = cursoService.insertNew(curso);
        return ResponseEntity.ok().body(cursoInserido);
    }
}
