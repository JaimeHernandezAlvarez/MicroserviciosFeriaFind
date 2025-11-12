package com.servicio.DetalleUsuario.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.DetalleUsuario.Assembler.DetalleUsuarioModelAssembler;
import com.servicio.DetalleUsuario.Model.DetalleUsuario;
import com.servicio.DetalleUsuario.Service.DetalleUsuarioService;
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
 * Controlador REST para la gestión de DetalleUsuario.
 * Incluye endpoints CRUD con soporte HATEOAS.
 */
@RestController
@RequestMapping("/api/v1/detalle-usuario")
@Tag(name = "DetalleUsuario Controller", description = "Controlador básico de DetalleUsuario con soporte HATEOAS")
public class DetalleUsuarioController {

    @Autowired
    private DetalleUsuarioService detalleUsuarioService;

    @Autowired
    private DetalleUsuarioModelAssembler assembler;

    // ------------------------------------------------------------
    // GET - Listar todos los detalles de usuario
    // ------------------------------------------------------------
    @SuppressWarnings("null")
    @Operation(summary = "Listar todos los detalles de usuario", description = "Devuelve una lista de todos los detalles de usuario con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DetalleUsuario>>> listar() {
        List<DetalleUsuario> detalles = detalleUsuarioService.findAll();

        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<DetalleUsuario>> detalleModels = detalles.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<DetalleUsuario>> collectionModel = CollectionModel.of(
                detalleModels,
                linkTo(methodOn(DetalleUsuarioController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear un nuevo detalle de usuario
    // ------------------------------------------------------------
    @Operation(summary = "Guardar nuevo detalle de usuario", description = "Crea un nuevo registro de detalle de usuario con enlace HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<DetalleUsuario>> guardar(@RequestBody DetalleUsuario detalleUsuario) {
        DetalleUsuario nuevo = detalleUsuarioService.save(detalleUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    // ------------------------------------------------------------
    // GET - Buscar detalle de usuario por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar detalle de usuario por ID", description = "Devuelve un registro de detalle de usuario con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DetalleUsuario>> buscar(@PathVariable Integer id) {
        try {
            DetalleUsuario detalle = detalleUsuarioService.findById(id);
            return ResponseEntity.ok(assembler.toModel(detalle));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar detalle de usuario existente
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar detalle de usuario", description = "Modifica un detalle de usuario existente con enlace HATEOAS")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DetalleUsuario>> actualizar(@PathVariable Integer id, @RequestBody DetalleUsuario detalleUsuario) {
        try {
            DetalleUsuario existente = detalleUsuarioService.findById(id);
            existente.setIdUsuario(detalleUsuario.getIdUsuario());
            existente.setIdVendedor(detalleUsuario.getIdVendedor());
            DetalleUsuario actualizado = detalleUsuarioService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar detalle de usuario
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar detalle de usuario", description = "Elimina un registro de detalle de usuario por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            detalleUsuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
