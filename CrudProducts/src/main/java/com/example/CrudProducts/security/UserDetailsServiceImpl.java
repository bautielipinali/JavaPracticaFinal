package com.example.CrudProducts.security;

import java.util.List;
import java.util.Set;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //carga el usuario por el nombre de usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var usuario = getById(username);

        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }

        return User
                .withUsername(username)
                .password(usuario.password())
                .roles(usuario.roles().toArray(new String[0]))
                .build();
    }

    public record Usuario(String username, String password, Set<String> roles) {};

    //buscar un usuario a traves del id
    public static Usuario getById(String username) {

        var password = "$2a$10$RzL9CeEZ2OBzSJXGj0oOPut2wP/jZd5rrA/Fj48mAUB86Osf2WyLG"; //contraseña encriptada

        Usuario adminUser = new Usuario(
                "Admin",
                password,
                Set.of("USER")
        );

        var usuarios = List.of(adminUser);

        return usuarios
                .stream()
                .filter(e -> e.username().equals(username))
                .findFirst()
                .orElse(null);
    }

}

