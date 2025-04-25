package br.com.dom.inglesadm_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
@Entity(name = "usuario")
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_usu_id")
    private Long id;

    @Column(name = "usu_login")
    private String login;

    @Column(name = "usu_senha")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "usu_role", nullable = false)
    private Role role;

    @Column(name = "usu_status")
    private String status;

    // Construtor para criar um usuário apenas com login pass e role
    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

}
