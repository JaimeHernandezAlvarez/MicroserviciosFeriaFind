package com.servicio.Categoria.Config;

import com.servicio.Categoria.Model.Categoria;
import com.servicio.Categoria.Repository.CategoriaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoriaDataLoader {

    private final CategoriaRepository categoriaRepository;
    private final Faker faker = new Faker();

    @PostConstruct
    public void init() {
        if (categoriaRepository.count() == 0) {
            for (int i = 0; i < 10; i++) { // 🔢 Genera 10 registros falsos
                Categoria categoria = new Categoria();
                categoria.setNombreCategoria(faker.commerce().department()); // 🏷️ Nombre de categoría
                categoriaRepository.save(categoria);
            }
            System.out.println("✅ Datos falsos de Categoría generados correctamente.");
        }
    }
}
