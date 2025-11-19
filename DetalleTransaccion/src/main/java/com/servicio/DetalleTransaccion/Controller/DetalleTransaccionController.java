package com.servicio.DetalleTransaccion.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.DetalleTransaccion.Assembler.DetalleTransaccionModelAssembler;
import com.servicio.DetalleTransaccion.Model.DetalleTransaccion;
import com.servicio.DetalleTransaccion.Service.DetalleTransaccionService;

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
@RequestMapping("/api/v1/detalles-transaccion")
@Tag(name = "DetalleTransaccion Controller", description = "Controlador básico de detalles de transacción con soporte HATEOAS")
public class DetalleTransaccionController {

    @Autowired
    private DetalleTransaccionService detalleService;

    @Autowired
    private DetalleTransaccionModelAssembler assembler;

    // ------------------------------------------------------------
    // GET - Listar todos los detalles
    // ------------------------------------------------------------
    @SuppressWarnings("null")
    @Operation(summary = "Listar todos los detalles", description = "Devuelve una lista de detalles con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DetalleTransaccion>>> listar() {

        List<DetalleTransaccion> detalles = detalleService.findAll();

        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<DetalleTransaccion>> detalleModels = detalles.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<DetalleTransaccion>> collectionModel = CollectionModel.of(
                detalleModels,
                linkTo(methodOn(DetalleTransaccionController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear un nuevo detalle
    // ------------------------------------------------------------
    @Operation(summary = "Guardar nuevo detalle", description = "Crea un detalle de transacción con enlaces HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<DetalleTransaccion>> guardar(@RequestBody DetalleTransaccion detalle) {
        DetalleTransaccion nuevo = detalleService.save(detalle);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    // ------------------------------------------------------------
    // GET - Buscar detalle por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar detalle por ID", description = "Devuelve los datos del detalle con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DetalleTransaccion>> buscar(@PathVariable Integer id) {
        try {
            DetalleTransaccion detalle = detalleService.findById(id);
            return ResponseEntity.ok(assembler.toModel(detalle));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar detalle existente
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar detalle", description = "Actualiza un detalle existente con enlaces HATEOAS")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DetalleTransaccion>> actualizar(@PathVariable Integer id, @RequestBody DetalleTransaccion detalle) {

        try {
            DetalleTransaccion existente = detalleService.findById(id);

            existente.setCantidad(detalle.getCantidad());
            existente.setPrecioUnitario(detalle.getPrecioUnitario());
            existente.setSubtotal(detalle.getSubtotal());
            existente.setIdProducto(detalle.getIdProducto());
            existente.setIdTransaccion(detalle.getIdTransaccion());

            DetalleTransaccion actualizado = detalleService.save(existente);

            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar detalle
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar detalle", description = "Elimina un detalle de transacción por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            detalleService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
