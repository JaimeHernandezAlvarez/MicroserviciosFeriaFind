package com.servicio.DetalleProducto.Config;

import com.servicio.DetalleProducto.Model.DetalleProducto;
import com.servicio.DetalleProducto.Repository.DetalleProductoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetalleProductoDataLoader {

    private final DetalleProductoRepository detalleProductoRepository;
    private final Faker faker = new Faker();

    @PostConstruct
    public void init() {
        if (detalleProductoRepository.count() == 0) {

            for (int i = 0; i < 20; i++) { // 🔢 Genera 20 registros falsos
                DetalleProducto detalle = new DetalleProducto();

                // IdProducto y IdCategoria como enteros (simulando FK)
                detalle.setIdProducto(faker.number().numberBetween(1, 50));
                detalle.setIdCategoria(faker.number().numberBetween(1, 10));

                detalleProductoRepository.save(detalle);
            }

            System.out.println("✅ Datos falsos de DetalleProducto generados correctamente.");
        }
    }
}

