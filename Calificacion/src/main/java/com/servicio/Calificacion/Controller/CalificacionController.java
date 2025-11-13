package com.servicio.Calificacion.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.Calificacion.Assembler.CalificacionModelAssembler;
import com.servicio.Calificacion.Model.Calificacion;
import com.servicio.Calificacion.Service.CalificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/calificaciones")
@Tag(name = "Calificación Controller", description = "Controlador básico de calificaciones con soporte HATEOAS")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @Autowired
    private CalificacionModelAssembler assembler;

    // ------------------------------------------------------------
    // GET - Listar todas las calificaciones
    // ------------------------------------------------------------
    @SuppressWarnings("null")
    @Operation(summary = "Listar todas las calificaciones", description = "Devuelve una lista de todas las calificaciones con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Calificacion>>> listar() {
        List<Calificacion> calificaciones = calificacionService.findAll();

        if (calificaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Calificacion>> calificacionesModel = calificaciones.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Calificacion>> collectionModel = CollectionModel.of(
                calificacionesModel,
                linkTo(methodOn(CalificacionController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear una nueva calificación
    // ------------------------------------------------------------
    @Operation(summary = "Guardar nueva calificación", description = "Crea una nueva calificación en la base de datos con enlace HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<Calificacion>> guardar(@RequestBody Calificacion calificacion) {
        Calificacion nueva = calificacionService.save(calificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nueva));
    }

    // ------------------------------------------------------------
    // GET - Buscar calificación por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar calificación por ID", description = "Devuelve los datos de una calificación con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Calificacion>> buscar(@PathVariable Integer id) {
        try {
            Calificacion calificacion = calificacionService.findById(id);
            return ResponseEntity.ok(assembler.toModel(calificacion));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar calificación existente
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar una calificación", description = "Modifica los datos de una calificación existente con enlace HATEOAS")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Calificacion>> actualizar(@PathVariable Integer id, @RequestBody Calificacion calificacion) {
        try {
            Calificacion existente = calificacionService.findById(id);
            existente.setValor(calificacion.getValor());
            existente.setComentario(calificacion.getComentario());
            existente.setFecha(calificacion.getFecha());
            existente.setIdUsuario(calificacion.getIdUsuario());
            existente.setIdVendedor(calificacion.getIdVendedor());

            Calificacion actualizada = calificacionService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar calificación
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar calificación", description = "Elimina una calificación de la base de datos por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            calificacionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
