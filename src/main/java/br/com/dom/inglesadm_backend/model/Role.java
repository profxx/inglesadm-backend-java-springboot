package br.com.dom.inglesadm_backend.model;


public enum Role {
    USER("user"),
    ADMIN("admin");

    private String role;

    // Construtor
    Role(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
