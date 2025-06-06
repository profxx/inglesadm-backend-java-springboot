package br.com.dom.inglesadm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dom.inglesadm_backend.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>{

}
