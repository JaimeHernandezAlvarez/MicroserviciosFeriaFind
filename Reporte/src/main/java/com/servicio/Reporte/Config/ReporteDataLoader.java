package com.servicio.Reporte.Config;

import com.servicio.Reporte.Model.Reporte;
import com.servicio.Reporte.Repository.ReporteRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ReporteDataLoader {

    private final ReporteRepository reporteRepository;
    private final Faker faker = new Faker();

    @PostConstruct
    public void init() {
        if (reporteRepository.count() == 0) {

            for (int i = 0; i < 10; i++) {
                Reporte reporte = new Reporte();

                reporte.setTipoReporte(faker.lorem().word());
                reporte.setComentario(faker.lorem().sentence(10));
                reporte.setEstado(faker.options().option("Pendiente", "Revisado", "Cerrado"));

                // Fecha -> LocalDate (modelo usa LocalDate)
                Date fechaFake = faker.date().past(30, TimeUnit.DAYS);
                LocalDate fecha = fechaFake.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                reporte.setFecha(fecha);

                reporte.setIdUsuario(faker.number().numberBetween(1, 20));
                reporte.setIdVendedor(faker.number().numberBetween(1, 20));

                reporteRepository.save(reporte);
            }

            System.out.println("✅ Datos falsos de Reporte generados correctamente.");
        }
    }
}
