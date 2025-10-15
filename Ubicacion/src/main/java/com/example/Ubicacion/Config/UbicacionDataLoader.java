package com.example.Ubicacion.Config; // Asegúrate de que este sea el paquete correcto

import com.example.Ubicacion.Model.Ubicacion;
import com.example.Ubicacion.Repository.UbicacionRepository; // Necesitarás importar el repositorio de Ubicacion
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UbicacionDataLoader {

    private final UbicacionRepository ubicacionRepository;
    // Usamos un Faker con configuración regional para datos más realistas (ej. "es-CL" para Chile)
    @SuppressWarnings("deprecation")
    private final Faker faker = new Faker(new Locale("es-CL"));

    @PostConstruct
    public void init() {
        // Solo se ejecuta si la tabla de ubicaciones está vacía
        if (ubicacionRepository.count() == 0) {
            for (int i = 0; i < 10; i++) { // 🔢 Genera 10 registros falsos
                Ubicacion ubicacion = new Ubicacion();

                // 🏢 Genera datos realistas para cada campo
                ubicacion.setNombreFeria("Feria de " + faker.commerce().department());
                ubicacion.setDireccion(faker.address().fullAddress());

                // 🌎 Genera coordenadas y las convierte a BigDecimal
                BigDecimal latitud = new BigDecimal(faker.address().latitude().replace(",", "."));
                BigDecimal longitud = new BigDecimal(faker.address().longitude().replace(",", "."));

                ubicacion.setLatitud(latitud);
                ubicacion.setLongitud(longitud);

                // 💾 Guarda la nueva entidad en la base de datos
                ubicacionRepository.save(ubicacion);
            }
            System.out.println("✅ Datos falsos de Ubicacion generados correctamente.");
        }
    }
}