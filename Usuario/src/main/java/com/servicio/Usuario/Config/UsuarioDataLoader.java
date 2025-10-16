package com.servicio.Usuario.Config; // Asegúrate de que el paquete sea el correcto

import com.servicio.Usuario.Model.Usuario;
import com.servicio.Usuario.Repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder; // 1. Importa el codificador
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UsuarioDataLoader {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // 2. Inyecta el codificador de contraseñas
    @SuppressWarnings("deprecation")
    private final Faker faker = new Faker(new Locale("es"));

    @PostConstruct
    public void init() {
        if (usuarioRepository.count() == 0) {
            for (int i = 0; i < 10; i++) {
                Usuario usuario = new Usuario();

                // 📧 Genera un correo electrónico falso y único
                usuario.setCorreoElectronico(faker.internet().emailAddress());

                // 🔐 Genera una contraseña y la codifica (hashea) antes de guardarla
                String contrasenaPlana = "password123"; // O usa faker.internet().password()
                usuario.setContrasena(passwordEncoder.encode(contrasenaPlana));

                usuarioRepository.save(usuario);
            }
            // ✨ Crea un usuario de prueba con datos conocidos para facilitar el login
            Usuario usuarioDePrueba = new Usuario();
            usuarioDePrueba.setCorreoElectronico("usuario@prueba.com");
            usuarioDePrueba.setContrasena(passwordEncoder.encode("admin")); // Contraseña es "admin"
            usuarioRepository.save(usuarioDePrueba);
            
            System.out.println("✅ Datos falsos de Usuario generados correctamente.");
        }
    }
}