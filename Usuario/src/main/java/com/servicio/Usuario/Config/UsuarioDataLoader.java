package com.servicio.Usuario.Config;

import com.servicio.Usuario.Model.Role; // IMPORTANTE: Importar el Enum
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
    
    // Evita advertencias de depreciación si usas versiones recientes de Faker
    private final Faker faker = new Faker(new Locale("es"));

    @PostConstruct
    public void init() {
        if (usuarioRepository.count() == 0) {
            
            // 1. Crear usuarios aleatorios (ROL USER)
            for (int i = 0; i < 10; i++) {
                Usuario usuario = new Usuario();

                usuario.setNombreUsuario(faker.name().username());
                usuario.setCorreoElectronico(faker.internet().emailAddress());
                usuario.setFoto(faker.internet().image());
                usuario.setDescripcion(faker.lorem().sentence(10));
                usuario.setHorario(faker.job().keySkills());
                
                // ASIGNACIÓN DE ROL:
                usuario.setRol(Role.USER);

                String contrasenaPlana = faker.internet().password(8, 12);
                usuario.setContrasena(passwordEncoder.encode(contrasenaPlana));

                usuarioRepository.save(usuario);
            }

            // 2. Usuario ADMIN fijo (Para que tú pruebes como Administrador)
            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            admin.setCorreoElectronico("admin@prueba.com"); // Cambié el correo para que sea más claro
            admin.setFoto("https://i.pravatar.cc/150?img=3");
            admin.setDescripcion("Usuario SUPER ADMINISTRADOR");
            admin.setHorario("24/7");
            admin.setRol(Role.ADMIN); // <--- ES ADMIN
            admin.setContrasena(passwordEncoder.encode("admin123")); 

            usuarioRepository.save(admin);

            // 3. Usuario USER fijo (Para pruebas manuales de usuario normal)
            Usuario userNormal = new Usuario();
            userNormal.setNombreUsuario("usuario");
            userNormal.setCorreoElectronico("user@prueba.com");
            userNormal.setFoto("https://i.pravatar.cc/150?img=5");
            userNormal.setDescripcion("Usuario normal de prueba");
            userNormal.setHorario("Lunes a Viernes");
            userNormal.setRol(Role.USER); // <--- ES USER
            userNormal.setContrasena(passwordEncoder.encode("user123"));

            usuarioRepository.save(userNormal);

            System.out.println("✅ Datos de prueba (Admin y User) generados correctamente.");
        }
    }
}