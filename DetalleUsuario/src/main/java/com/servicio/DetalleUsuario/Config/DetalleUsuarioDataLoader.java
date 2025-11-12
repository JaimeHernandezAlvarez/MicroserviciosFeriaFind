package com.servicio.DetalleUsuario.Config;

import com.servicio.DetalleUsuario.Model.DetalleUsuario;
import com.servicio.DetalleUsuario.Repository.DetalleUsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetalleUsuarioDataLoader {

    private final DetalleUsuarioRepository detalleUsuarioRepository;
    private final Faker faker = new Faker();

    @PostConstruct
    public void init() {
        if (detalleUsuarioRepository.count() == 0) {
            for (int i = 0; i < 10; i++) { // 🔢 Genera 10 registros falsos
                DetalleUsuario detalle = new DetalleUsuario();
                detalle.setIdUsuario(faker.number().numberBetween(1, 100));   // 🔸 ID de usuario aleatorio
                detalle.setIdVendedor(faker.number().numberBetween(1, 50));   // 🔸 ID de vendedor aleatorio
                detalleUsuarioRepository.save(detalle);
            }
            System.out.println("✅ Datos falsos de DetalleUsuario generados correctamente.");
        }
    }
}
