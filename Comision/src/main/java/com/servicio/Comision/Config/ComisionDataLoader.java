package com.servicio.Comision.Config;

import com.servicio.Comision.Model.Comision;
import com.servicio.Comision.Repository.ComisionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ComisionDataLoader {

    private final ComisionRepository comisionRepository;
    private final Faker faker = new Faker();

    @PostConstruct
    public void init() {
        if (comisionRepository.count() == 0) {
            for (int i = 0; i < 10; i++) {

                Comision comision = new Comision();

                // Porcentaje entre 1% y 20%
                comision.setPorcentaje(
                        BigDecimal.valueOf(faker.number().numberBetween(1, 20))
                );

                // Monto aplicado entre 1000 y 50000
                comision.setMontoAplicado(
                        BigDecimal.valueOf(faker.number().numberBetween(1000, 50000))
                );

                // id_transaccion simulado
                comision.setIdTransaccion(
                        faker.number().numberBetween(1, 20)
                );

                comisionRepository.save(comision);
            }

            System.out.println("✅ Datos falsos de Comision generados correctamente.");
        }
    }
}
