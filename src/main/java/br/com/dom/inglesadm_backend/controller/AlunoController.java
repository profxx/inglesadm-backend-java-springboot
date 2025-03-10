package br.com.dom.inglesadm_backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dom.inglesadm_backend.model.Aluno;
import br.com.dom.inglesadm_backend.service.AlunoService;

@RestController
@RequestMapping("alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    // Listar todos os alunos
    @GetMapping
    public ResponseEntity<List<Aluno>> findAll() {
        List<Aluno> students = alunoService.findAll();
        return ResponseEntity.ok().body(students);
    }

    // Listar os alunos ativos
    @GetMapping("ativos")
    public ResponseEntity<List<Aluno>> findActiveStudentsOrderedByName() {
        List<Aluno> students = alunoService.findActiveStudentsOrderedByName();
        return ResponseEntity.ok().body(students);
    }

    @GetMapping("inativos")
    public ResponseEntity<List<Aluno>> findInactiveStudentsOrderedByName() {
        List<Aluno> students = alunoService.findInactiveStudentsOrderedByName();
        return ResponseEntity.ok().body(students);
    }

    @GetMapping("/segunda")
    public List<Aluno> getStudentsOnMonday() {
        return alunoService.getStudentsByDay("Segunda-feira");
    }

    @GetMapping("/terca")
    public List<Aluno> getStudentsOnTuesday() {
        return alunoService.getStudentsByDay("Terça-feira");
    }

    @GetMapping("/quarta")
    public List<Aluno> getStudentsOnWednesday() {
        return alunoService.getStudentsByDay("Quarta-feira");
    }

    @GetMapping("/quinta")
    public List<Aluno> getStudentsOnThursday() {
        return alunoService.getStudentsByDay("Quinta-feira");
    }

    @GetMapping("/sexta")
    public List<Aluno> getStudentsOnFriday() {
        return alunoService.getStudentsByDay("Sexta-feira");
    }

    @GetMapping("/sabado")
    public List<Aluno> getStudentsOnSaturday() {
        return alunoService.getStudentsByDay("Sábado");
    }

    // Busca alunos pelo nome
    @GetMapping("/buscar")
    public List<Aluno> searchStudentsByName(@RequestParam("name") String name) {
        return alunoService.findStudentsByName(name);
    }

    // Inserir novo aluno
    @PostMapping
    public ResponseEntity<Aluno> insertNew(@RequestBody Aluno student) {
        try {
            Aluno studentInserted = alunoService.insertNew(student);
            return ResponseEntity.ok().body(studentInserted);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Listar alunos por curso
    @GetMapping("/curso/{courseId}")
    public ResponseEntity<List<Aluno>> findStudentsByCourseId(@PathVariable Long courseId) {
        List<Aluno> students = alunoService.findStudentsByCourse(courseId);
        return ResponseEntity.ok().body(students);
    }

    // Pegar número de alunos ativos pagantes
    @GetMapping("/quantidade")
    public ResponseEntity<Integer> numberOfActiveStudents() {
        int number = alunoService.numberOfActiveStudents();
        return ResponseEntity.ok().body(number);
    }

    @PutMapping("inativar/{id}")
    public ResponseEntity<Aluno> toInactivate(@PathVariable Long id){
        Aluno aluno = alunoService.toInactive(id);
        return ResponseEntity.ok().body(aluno);
    }
}
