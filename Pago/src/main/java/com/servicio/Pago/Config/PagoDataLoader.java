package com.servicio.Pago.Config;

import com.servicio.Pago.Model.Pago;
import com.servicio.Pago.Repository.PagoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class PagoDataLoader {

    private final PagoRepository pagoRepository;
    private final Faker faker = new Faker();

    @PostConstruct
    public void init() {
        if (pagoRepository.count() == 0) {
            for (int i = 0; i < 10; i++) {

                Pago pago = new Pago();
                pago.setMetodo(faker.options().option(
                        "Tarjeta Crédito",
                        "Tarjeta Débito",
                        "Transferencia",
                        "PayPal",
                        "Efectivo"
                ));
                pago.setEstado(faker.options().option(
                        "Completado",
                        "Pendiente",
                        "Rechazado"
                ));
                pago.setFechaPago(new Date());
                pago.setReferenciaPasarela("REF-" + faker.number().digits(8));

                pagoRepository.save(pago);
            }
            System.out.println("✅ Datos falsos de Pago generados correctamente.");
        }
    }
}
