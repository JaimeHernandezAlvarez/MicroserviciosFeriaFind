package com.service.Feria.Config;


import com.service.Feria.Model.Feria;
import com.service.Feria.Repository.FeriaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeriaDataLoader {

    private final FeriaRepository feriaRepository;
    private final Faker faker = new Faker();

    @PostConstruct
    public void init() {
        if (feriaRepository.count() == 0) {
            for (int i = 0; i < 10; i++) { // 🔢 Genera 10 registros falsos
                Feria feria = new Feria();
                feria.setNombre(faker.company().name()); // 🏢 Usa nombres de empresas como nombre de feria
                feriaRepository.save(feria);
            }
            System.out.println("✅ Datos falsos de Feria generados correctamente.");
        }
    }
}
