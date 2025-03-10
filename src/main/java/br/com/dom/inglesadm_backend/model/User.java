package br.com.dom.inglesadm_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_usu_id")
    private Long id;

    @Column(name = "usu_curso_id")
    private Long idCourse;

    @Column(name = "usu_nome")
    private String name;

    @Column(name = "usu_email")
    private String email;

    @Column(name = "usu_login")
    private String login;

    @Column(name = "usu_senha")
    private String password;
    
    @Column(name = "usu_phone")
    private String phone;


}
