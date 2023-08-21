package br.com.openumbrella.infinityv.api.repositories;

import br.com.openumbrella.infinityv.api.model.passwd.Passwd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswdRepository extends JpaRepository<Passwd, Long> {
}
