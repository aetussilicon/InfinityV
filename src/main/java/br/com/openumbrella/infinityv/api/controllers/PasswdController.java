package br.com.openumbrella.infinityv.api.controllers;


import br.com.openumbrella.infinityv.api.model.passwd.DataAddPasswd;
import br.com.openumbrella.infinityv.api.model.passwd.Passwd;
import br.com.openumbrella.infinityv.api.repositories.PasswdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("passwd")
public class PasswdController {

    @Autowired
    PasswdRepository passwdRepository;

    @PostMapping("/api/add")
    public void addPasswd(@RequestBody DataAddPasswd dataAddPasswd){
        passwdRepository.save(new Passwd(dataAddPasswd));
    }

}
