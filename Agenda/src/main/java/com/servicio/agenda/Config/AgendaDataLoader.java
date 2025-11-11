package com.servicio.agenda.Config;

import com.servicio.agenda.Model.Agenda;
import com.servicio.agenda.Repository.AgendaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class AgendaDataLoader {

    private final AgendaRepository agendaRepository;
    @SuppressWarnings("deprecation")
    private final Faker faker = new Faker(new Locale("es"));

    @PostConstruct
    public void init() {
        if (agendaRepository.count() == 0) {
            String[] diasSemana = {
                    "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
            };

            for (int i = 0; i < 10; i++) {
                Agenda agenda = new Agenda();

                agenda.setDiaSemana(diasSemana[faker.number().numberBetween(0, diasSemana.length)]);
                agenda.setHoraInicio(LocalTime.of(faker.number().numberBetween(8, 12), 0)); // entre 8:00 y 11:59
                agenda.setHoraTermino(LocalTime.of(faker.number().numberBetween(13, 20), 0)); // entre 13:00 y 19:59
                agenda.setIdVendedor(faker.number().numberBetween(1, 10)); // IDs simulados
                agenda.setIdUbicacion(faker.number().numberBetween(1, 5));

                agendaRepository.save(agenda);
            }

            System.out.println("✅ Datos falsos de Agenda generados correctamente.");
        }
    }
}
