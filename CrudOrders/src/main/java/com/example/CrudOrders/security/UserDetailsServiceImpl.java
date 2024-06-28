package com.example.CrudOrders.security;

import java.util.List;
import java.util.Set;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //Cargar Usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var usuario = getById(username);

        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }

        return User // Construye y retorna un objeto UserDetails con los datos del usuario.
                .withUsername(username)
                .password(usuario.password())
                .roles(usuario.roles().toArray(new String[0]))
                .build();
    }

    public record Usuario(String username, String password, Set<String> roles) {};

    //Buscar por id un usuario
    public static Usuario getById(String username) {

        var password = "$2a$10$6VCtW4suls0CRnmfSUGP3.K3rzXTFQQMrFK4ypTigihZK66Gm7unq";

        Usuario adminUser = new Usuario(
                "Admin",
                password,
                Set.of("USER")
        );

        var usuarios = List.of(adminUser); // Lista de usuarios

        return usuarios  // Busca y retorna el usuario
                .stream()
                .filter(e -> e.username().equals(username))
                .findFirst()
                .orElse(null);
    }

}

