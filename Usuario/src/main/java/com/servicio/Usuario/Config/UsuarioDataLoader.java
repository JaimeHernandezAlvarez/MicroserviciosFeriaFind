package com.servicio.Usuario.Config;

import com.servicio.Usuario.Model.Usuario;
import com.servicio.Usuario.Repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UsuarioDataLoader {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    @SuppressWarnings("deprecation")
    private final Faker faker = new Faker(new Locale("es"));

    @PostConstruct
    public void init() {
        if (usuarioRepository.count() == 0) {
            for (int i = 0; i < 10; i++) {
                Usuario usuario = new Usuario();

                usuario.setNombreUsuario(faker.name().username());
                usuario.setCorreoElectronico(faker.internet().emailAddress());
                usuario.setFoto(faker.internet().image());
                usuario.setDescripcion(faker.lorem().sentence(10));
                usuario.setHorario(faker.job().keySkills());

                // 🔐 Genera y encripta la contraseña
                String contrasenaPlana = faker.internet().password(8, 12);
                usuario.setContrasena(passwordEncoder.encode(contrasenaPlana));

                usuarioRepository.save(usuario);
            }

            // ✨ Usuario de prueba para desarrollo o login manual
            Usuario usuarioDePrueba = new Usuario();
            usuarioDePrueba.setNombreUsuario("admin");
            usuarioDePrueba.setCorreoElectronico("usuario@prueba.com");
            usuarioDePrueba.setFoto("https://i.pravatar.cc/150?img=3");
            usuarioDePrueba.setDescripcion("Usuario administrador de prueba");
            usuarioDePrueba.setHorario("Lunes a Viernes, 9:00 - 18:00");
            usuarioDePrueba.setContrasena(passwordEncoder.encode("admin")); // Contraseña: admin

            usuarioRepository.save(usuarioDePrueba);

            System.out.println("✅ Datos falsos de Usuario generados correctamente.");
        }
    }
}
