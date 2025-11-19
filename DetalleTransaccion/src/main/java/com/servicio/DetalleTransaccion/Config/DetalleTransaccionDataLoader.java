package com.servicio.DetalleTransaccion.Config;

import com.servicio.DetalleTransaccion.Model.DetalleTransaccion;
import com.servicio.DetalleTransaccion.Repository.DetalleTransaccionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetalleTransaccionDataLoader {

    private final DetalleTransaccionRepository detalleRepository;
    private final Faker faker = new Faker();

    @PostConstruct
    public void init() {
        if (detalleRepository.count() == 0) {
            for (int i = 0; i < 10; i++) { // 🔢 Genera 10 registros falsos
                DetalleTransaccion detalle = new DetalleTransaccion();

                detalle.setCantidad(faker.number().numberBetween(1, 10)); // Cantidad entre 1 y 10
                detalle.setPrecioUnitario(faker.number().randomDouble(2, 1000, 20000)); // Precio entre 1.000 y 20.000
                detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());

                // 🔗 IDs simulados (se deben reemplazar por relaciones reales si ya existen Product y Transaccion)
                detalle.setIdProducto(faker.number().numberBetween(1, 5));
                detalle.setIdTransaccion(faker.number().numberBetween(1, 5));

                detalleRepository.save(detalle);
            }
            System.out.println("✅ Datos falsos de DetalleTransaccion generados correctamente.");
        }
    }
}
