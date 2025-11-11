package com.servicio.agenda.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.agenda.Assembler.AgendaModelAssembler;
import com.servicio.agenda.Model.Agenda;
import com.servicio.agenda.Service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Controlador REST para la gestión de agendas con soporte HATEOAS.
 */
@RestController
@RequestMapping("/api/v1/agendas")
@Tag(name = "Agenda Controller", description = "Controlador básico de agendas con soporte HATEOAS")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private AgendaModelAssembler assembler;

    // ------------------------------------------------------------
    // GET - Listar todas las agendas
    // ------------------------------------------------------------
    @Operation(summary = "Listar todas las agendas", description = "Devuelve una lista de todas las agendas con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Agenda>>> listar() {
        List<Agenda> agendas = agendaService.findAll();

        if (agendas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Agenda>> agendaModels = agendas.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        @SuppressWarnings("null")
        CollectionModel<EntityModel<Agenda>> collectionModel = CollectionModel.of(
                agendaModels,
                linkTo(methodOn(AgendaController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear nueva agenda
    // ------------------------------------------------------------
    @Operation(summary = "Guardar nueva agenda", description = "Crea una nueva agenda en la base de datos con enlace HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<Agenda>> guardar(@RequestBody Agenda agenda) {
        Agenda nueva = agendaService.save(agenda);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nueva));
    }

    // ------------------------------------------------------------
    // GET - Buscar agenda por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar agenda por ID", description = "Devuelve los datos de una agenda con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Agenda>> buscar(@PathVariable Integer id) {
        try {
            Agenda agenda = agendaService.findById(id);
            return ResponseEntity.ok(assembler.toModel(agenda));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar agenda existente
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar una agenda", description = "Modifica los datos de una agenda existente con enlace HATEOAS")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Agenda>> actualizar(@PathVariable Integer id, @RequestBody Agenda agenda) {
        try {
            Agenda existente = agendaService.findById(id);
            existente.setDiaSemana(agenda.getDiaSemana());
            existente.setHoraInicio(agenda.getHoraInicio());
            existente.setHoraTermino(agenda.getHoraTermino());
            existente.setIdVendedor(agenda.getIdVendedor());
            existente.setIdUbicacion(agenda.getIdUbicacion());

            Agenda actualizada = agendaService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar agenda
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar agenda", description = "Elimina una agenda de la base de datos por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            agendaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
