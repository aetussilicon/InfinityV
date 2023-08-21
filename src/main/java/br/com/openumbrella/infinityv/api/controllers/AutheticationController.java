package br.com.openumbrella.infinityv.api.controllers;

import br.com.openumbrella.infinityv.api.model.users.DataAuth;
import br.com.openumbrella.infinityv.api.model.users.DataRegister;
import br.com.openumbrella.infinityv.api.model.users.User;
import br.com.openumbrella.infinityv.api.repositories.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid DataAuth dataAuth){
        var usernamePasswd = new UsernamePasswordAuthenticationToken(dataAuth.email(), dataAuth.password());
        var auth = this.authenticationManager.authenticate(usernamePasswd);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid DataRegister dataRegister){
        if(this.userRepository.findByEmail(dataRegister.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPasswd = new BCryptPasswordEncoder().encode(dataRegister.password());
        User newUser = new User(dataRegister.email(), dataRegister.username(), encryptedPasswd);

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
