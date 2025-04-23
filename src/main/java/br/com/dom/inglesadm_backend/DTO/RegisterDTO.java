package br.com.dom.inglesadm_backend.DTO;

import br.com.dom.inglesadm_backend.model.Role;

public record RegisterDTO(String login, String password, Role role) {
    
}
