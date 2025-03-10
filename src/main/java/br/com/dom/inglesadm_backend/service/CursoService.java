package br.com.dom.inglesadm_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dom.inglesadm_backend.model.Curso;
import br.com.dom.inglesadm_backend.repository.CursoRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> findAll(){
        return cursoRepository.findAll();
    }

    public Curso findById(Long id){
        return cursoRepository.findById(id).orElse(null);
    }

    public Curso insertNew(Curso curso){
        return cursoRepository.save(curso);
    }
}
