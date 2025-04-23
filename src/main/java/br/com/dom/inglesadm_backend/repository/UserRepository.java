package br.com.dom.inglesadm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.dom.inglesadm_backend.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);
}
