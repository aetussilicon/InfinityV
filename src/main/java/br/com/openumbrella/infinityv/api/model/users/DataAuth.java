package br.com.openumbrella.infinityv.api.model.users;

import jakarta.validation.constraints.Email;

public record DataAuth(
        @Email
        String email,
        String password) {
}
