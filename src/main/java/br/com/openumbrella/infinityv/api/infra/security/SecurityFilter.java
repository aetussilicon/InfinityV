package br.com.openumbrella.infinityv.api.infra.security;

import br.com.openumbrella.infinityv.api.repositories.UserRepository;
import br.com.openumbrella.infinityv.api.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    // Filtra as requisições para autenticação e autorização
    @Autowired
    UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Valida o token e obtém as informações de login
        var token = this.recoverToken(request);
        // Encontra os detalhes do usuário a partir do repositório
        if (token !=null){
            var login = tokenService.validateToken(token);
            UserDetails user = userRepository.findByEmail(login);

            // Cria uma autenticação baseada nas informações do usuário
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    // Recupera o token do cabeçalho da requisição
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Autorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer", "");
    }

}
