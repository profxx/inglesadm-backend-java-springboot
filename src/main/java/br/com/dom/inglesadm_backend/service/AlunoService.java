package br.com.dom.inglesadm_backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dom.inglesadm_backend.model.Aluno;
import br.com.dom.inglesadm_backend.repository.AlunoRepository;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository studentRepository;

    // Lista todos os alunos
    public List<Aluno> findAll(){
        return studentRepository.findAll();
    }
    // Lista Alunos Ativos
    public List<Aluno> findActiveStudentsOrderedByName(){
        return studentRepository.findActiveStudentsOrderedByName();
    }
    // Lista Alunos Inativos
    public List<Aluno> findInactiveStudentsOrderedByName(){
        return studentRepository.findInactiveStudentsOrderedByName();
    }
    // Lista Alunos pelo Dia
    public List<Aluno> getStudentsByDay(String dayOfWeek) {
        return studentRepository.getStudentsByDay(dayOfWeek);
    }

    // Busca aluno pelo nome
    public List<Aluno> findStudentsByName(String name) {
        return studentRepository.findByNameContaining(name);
    }

    // Inserir novo aluno
    public Aluno insertNew(Aluno newStudent){
        // id, nome, telefone, valor, course, dataEntrada, dia, hora, livro
        newStudent.setDataEntrada(LocalDate.now());
        return studentRepository.save(newStudent);
    }

    // Find By Id
    public Aluno findById(Long id){
        return studentRepository.findById(id).orElse(null);
    }

    // Tornar inativo
    public Aluno toInactive(Long id){
        Aluno aluno = findById(id);
        aluno.setDia("Inativo");
        return studentRepository.save(aluno);
    }

    // Deletar aluno
    public Boolean deleteById(Long id){
        Aluno student = findById(id);
        if (student == null){
            return false;
        }else{
            studentRepository.deleteById(id);
            return true;
        }
    }

    // Listar alunos por curso
    public List<Aluno> findStudentsByCourse(Long cursoId){
        return studentRepository.findStudentsByCurso_Id(cursoId);
    }

    // Pegar quantidade de alunos ativos pagantes
    public int numberOfActiveStudents(){
        return studentRepository.numberOfActiveStudents();
    }

}
