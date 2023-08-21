package br.com.openumbrella.infinityv.api.model.passwd;

//DTO para registro de senhas
public record DataAddPasswd(
        String website,
        String username,
        String password
) {
}
