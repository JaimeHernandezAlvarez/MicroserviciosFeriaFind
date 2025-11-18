package com.servicio.Producto.Config;

import com.servicio.Producto.Model.Producto;
import com.servicio.Producto.Repository.ProductoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductoDataLoader {

    private final ProductoRepository productoRepository;
    private final Faker faker = new Faker();

    @PostConstruct
    public void init() {
        if (productoRepository.count() == 0) {
            for (int i = 0; i < 10; i++) { // 🔢 Genera 10 registros falsos
                Producto producto = new Producto();

                producto.setNombre(faker.commerce().productName());
                producto.setPrecio(Double.valueOf(faker.commerce().price()));
                producto.setUnidadMedida(faker.options().option("kg", "unidad", "litro", "paquete"));
                producto.setImagen(faker.internet().image());
                producto.setIdVendedor(faker.number().numberBetween(1, 10));
                producto.setIdCategoria(faker.number().numberBetween(1, 10));

                productoRepository.save(producto);
            }
            System.out.println("✅ Datos falsos de Producto generados correctamente.");
        }
    }
}
