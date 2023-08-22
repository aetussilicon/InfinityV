package br.com.openumbrella.infinityv.api.controllers;

import br.com.openumbrella.infinityv.api.model.users.DataAuth;
import br.com.openumbrella.infinityv.api.model.users.DataLoginResponse;
import br.com.openumbrella.infinityv.api.model.users.DataRegister;
import br.com.openumbrella.infinityv.api.model.users.User;
import br.com.openumbrella.infinityv.api.repositories.UserRepository;
import br.com.openumbrella.infinityv.api.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AutheticationController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid DataAuth dataAuth){

        // Autenticação baseada em email e senha
        var usernamePasswd = new UsernamePasswordAuthenticationToken(dataAuth.email(), dataAuth.password());
        var auth = this.authenticationManager.authenticate(usernamePasswd);

        // Gera token JWT e o retorna
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new DataLoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid DataRegister dataRegister){

        //Verifica se o email já está registrado
        if(this.userRepository.findByEmail(dataRegister.email()) != null) return ResponseEntity.badRequest().build();

        //Encriptação da senha
        String encryptedPasswd = new BCryptPasswordEncoder().encode(dataRegister.password());
        User newUser = new User(dataRegister.email(), dataRegister.username(), encryptedPasswd);

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
