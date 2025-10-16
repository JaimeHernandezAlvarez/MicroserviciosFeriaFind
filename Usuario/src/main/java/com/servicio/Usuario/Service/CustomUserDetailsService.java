package com.servicio.Usuario.Service; // Asegúrate de que el paquete sea el correcto

import com.servicio.Usuario.Model.Usuario;
import com.servicio.Usuario.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections; // Import para la lista vacía de roles

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Spring nos da el "username", nosotros lo tratamos como si fuera el correo electrónico
        System.out.println("Buscando usuario con correo: " + username); // Log para depurar

        // 2. Buscamos el usuario en nuestra base de datos usando el repositorio
        Usuario miUsuario = usuarioRepository.findByCorreoElectronico(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + username));

        // 3. Convertimos nuestro objeto Usuario a un objeto User que Spring Security entiende
        //    Le pasamos el correo, la contraseña (ya hasheada) y una lista de roles (vacía por ahora)
        return new User(miUsuario.getCorreoElectronico(), miUsuario.getContrasena(), Collections.emptyList());
    }
}