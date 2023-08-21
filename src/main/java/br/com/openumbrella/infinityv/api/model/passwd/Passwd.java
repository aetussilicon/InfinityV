package br.com.openumbrella.infinityv.api.model.passwd;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "lockapi")
@Entity(name = "passwd")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Passwd {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String website;
    private String username;
    private String password;

    public Passwd(DataAddPasswd dataAddPasswd) {
        this.website = dataAddPasswd.website();
        this.username = dataAddPasswd.username();
        this.password = dataAddPasswd.password();
    }
}
