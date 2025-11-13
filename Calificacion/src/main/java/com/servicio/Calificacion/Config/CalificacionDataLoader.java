package com.servicio.Calificacion.Config;

import com.servicio.Calificacion.Model.Calificacion;
import com.servicio.Calificacion.Repository.CalificacionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class CalificacionDataLoader {

    private final CalificacionRepository calificacionRepository;
    private final Faker faker = new Faker();

    @PostConstruct
    public void init() {
        if (calificacionRepository.count() == 0) {
            for (int i = 0; i < 10; i++) { // 🔢 Genera 10 calificaciones falsas
                Calificacion calificacion = new Calificacion();

                // ⭐ Valor de 1 a 5
                calificacion.setValor(faker.number().numberBetween(1, 6));

                // 💬 Comentario falso realista
                calificacion.setComentario(faker.lorem().sentence(10));

                // 📅 Fecha aleatoria entre los últimos 30 días
                calificacion.setFecha(LocalDate.now().minusDays(ThreadLocalRandom.current().nextInt(0, 30)));

                // 👤 IDs de usuario y vendedor ficticios (puedes ajustar según tus datos reales)
                calificacion.setIdUsuario(faker.number().numberBetween(1, 10));
                calificacion.setIdVendedor(faker.number().numberBetween(1, 10));

                calificacionRepository.save(calificacion);
            }

            System.out.println("✅ Datos falsos de Calificación generados correctamente.");
        }
    }
}
