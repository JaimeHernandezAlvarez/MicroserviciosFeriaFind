package com.servicio.Transaccion.Config;

import com.servicio.Transaccion.Model.Transaccion;
import com.servicio.Transaccion.Repository.TransaccionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * DataLoader para Transaccion — genera datos falsos si la tabla está vacía.
 * Sigue el mismo estilo y formato que los DataLoaders anteriores (Feria, Producto, ...).
 */
@Component
@RequiredArgsConstructor
public class TransaccionDataLoader {

    private final TransaccionRepository transaccionRepository;
    private final Faker faker = new Faker();

    @PostConstruct
    public void init() {
        if (transaccionRepository.count() == 0) {

            for (int i = 0; i < 10; i++) { // 🔢 Genera 10 registros falsos
                Transaccion t = new Transaccion();

                // Fecha: usamos java.util.Date convertida a java.sql.Date (porque el modelo tiene java.sql.Date)
                Date fechaFake = faker.date().past(20, java.util.concurrent.TimeUnit.DAYS);
                t.setFecha(new java.sql.Date(fechaFake.getTime()));

                // MontoTotal: Double (modelo usa Double), generamos un double realista
                double monto = faker.number().randomDouble(2, 10000, 200000); // ejemplo: 12345.67
                t.setMontoTotal(monto);

                // Estado aleatorio
                t.setEstado(faker.options().option("PENDIENTE", "PAGADO", "CANCELADO"));

                // Ids de relaciones — enteros simulados
                t.setIdUsuario(faker.number().numberBetween(1, 10));
                t.setIdVendedor(faker.number().numberBetween(1, 10));
                t.setIdPago(faker.number().numberBetween(1, 5));

                transaccionRepository.save(t);
            }

            System.out.println("✅ Datos falsos de Transaccion generados correctamente.");
        }
    }
}
