package br.com.dom.inglesadm_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dom.inglesadm_backend.DTO.LoginResponseDTO;
import br.com.dom.inglesadm_backend.DTO.RegisterDTO;
import br.com.dom.inglesadm_backend.DTO.UserDTO;
import br.com.dom.inglesadm_backend.config.security.TokenService;
import br.com.dom.inglesadm_backend.model.User;
import br.com.dom.inglesadm_backend.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {
 
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid UserDTO userDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(userDTO.login(), userDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
    
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO newUser){
        if (userRepository.findByLogin(newUser.login()) != null) return ResponseEntity.badRequest().build();
        
        // Caso ainda não exista um usuário com o mesmo nome de login
        String encryptedPassword = new BCryptPasswordEncoder().encode(newUser.password());
        User user = new User(newUser.login(), encryptedPassword, newUser.role());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }


    
}
