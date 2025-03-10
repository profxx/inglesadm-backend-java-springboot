package br.com.dom.inglesadm_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.dom.inglesadm_backend.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query(value = "SELECT * FROM aluno WHERE dia <> 'Inativo' AND valor != 0 ORDER BY nome", nativeQuery = true)
    public List<Aluno> findActiveStudentsOrderedByName();

    @Query(value = "SELECT * FROM aluno WHERE dia = 'Inativo' ORDER BY nome", nativeQuery = true)
    List<Aluno> findInactiveStudentsOrderedByName();

    // Consulta SQL nativa para listar alunos por dia da semana
    @Query(value = "SELECT * FROM aluno WHERE dia = :dia ORDER BY hora", nativeQuery = true)
    List<Aluno> getStudentsByDay(@Param("dia") String dia);

    // Busca alunos pelo nome
    @Query(value = "SELECT * FROM students WHERE name LIKE CONCAT('%', :name, '%') ORDER BY name", nativeQuery = true)
    List<Aluno> findByNameContaining(@Param("name") String name);

    // Buscar alunos por curso
    List<Aluno> findStudentsByCurso_Id(Long cursoId);

    // Pegar o número de alunos ativos
    @Query(value = "SELECT count(*) FROM aluno WHERE dia != 'Inativo' AND valor != 0", nativeQuery = true)
    int numberOfActiveStudents();

}
