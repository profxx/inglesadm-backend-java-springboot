package br.com.dom.inglesadm_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import br.com.dom.inglesadm_backend.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

}
