package com.servicio.Vendedor.Config; // Paquete actualizado a "servicio"

import com.servicio.Vendedor.Model.Vendedor;
import com.servicio.Vendedor.Repository.VendedorRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@SuppressWarnings("deprecation")
@Component
@RequiredArgsConstructor
public class VendedorDataLoader {

    private final VendedorRepository vendedorRepository;
    
    private final Faker faker = new Faker(new Locale("es-CL"));
    private final Random random = new Random();

    @PostConstruct
    public void init() {
        // Solo se ejecuta si la tabla de vendedores está vacía
        if (vendedorRepository.count() == 0) {
            
            String[] estados = {"Activo", "Pendiente", "Inactivo"};

            for (int i = 0; i < 10; i++) { // 🔢 Genera 10 registros falsos
                Vendedor vendedor = new Vendedor();

                // 🏢 Genera datos realistas para cada campo
                vendedor.setNombreVendedor(faker.company().name());
                vendedor.setDescripcion(faker.lorem().paragraph(3)); // Descripción larga
                vendedor.setEstado(estados[random.nextInt(estados.length)]); // Selecciona un estado aleatorio
                vendedor.setFotoPerfil(faker.avatar().image()); // URL de una imagen de avatar

                // 3. Asigna un ID de usuario FALSO (aleatorio).
                // Asumimos que los IDs de usuario son números enteros positivos.
                vendedor.setIdUsuario(random.nextInt(1, 101)); // Genera un ID de usuario entre 1 y 100

                // 💾 Guarda la nueva entidad en la base de datos
                vendedorRepository.save(vendedor);
            }
            System.out.println("✅ Datos falsos de Vendedor generados correctamente (con IDs de usuario simulados).");
        }
    }
}