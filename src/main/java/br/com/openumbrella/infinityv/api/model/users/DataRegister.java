package br.com.openumbrella.infinityv.api.model.users;

import jakarta.validation.constraints.Email;

public record DataRegister(

        @Email
        String email,
        String username,
        String password) {
}
