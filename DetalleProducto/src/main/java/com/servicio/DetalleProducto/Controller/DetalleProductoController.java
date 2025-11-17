package com.servicio.DetalleProducto.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.DetalleProducto.Assembler.DetalleProductoModelAssembler;
import com.servicio.DetalleProducto.Model.DetalleProducto;
import com.servicio.DetalleProducto.Service.DetalleProductoService;

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
@RequestMapping("/api/v1/detalles-producto")
@Tag(name = "DetalleProducto Controller", description = "Controlador de DetalleProducto con soporte HATEOAS")
public class DetalleProductoController {

    @Autowired
    private DetalleProductoService detalleProductoService;

    @Autowired
    private DetalleProductoModelAssembler assembler;

    // ------------------------------------------------------------
    // GET - Listar todos los detalles
    // ------------------------------------------------------------
    @Operation(summary = "Listar todos los detalles de producto")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DetalleProducto>>> listar() {
        List<DetalleProducto> detalles = detalleProductoService.findAll();

        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<DetalleProducto>> detalleModel = detalles.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        @SuppressWarnings("null")
        CollectionModel<EntityModel<DetalleProducto>> collectionModel = CollectionModel.of(
                detalleModel,
                linkTo(methodOn(DetalleProductoController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear nuevo detalle
    // ------------------------------------------------------------
    @Operation(summary = "Crear un detalle de producto")
    @PostMapping
    public ResponseEntity<EntityModel<DetalleProducto>> guardar(@RequestBody DetalleProducto detalle) {
        DetalleProducto nuevo = detalleProductoService.save(detalle);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    // ------------------------------------------------------------
    // GET - Buscar detalle por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar detalle por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DetalleProducto>> buscar(@PathVariable Integer id) {
        try {
            DetalleProducto detalle = detalleProductoService.findById(id);
            return ResponseEntity.ok(assembler.toModel(detalle));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar detalle
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar detalle por ID")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DetalleProducto>> actualizar(@PathVariable Integer id, @RequestBody DetalleProducto detalle) {
        try {
            DetalleProducto existente = detalleProductoService.findById(id);

            existente.setIdProducto(detalle.getIdProducto());
            existente.setIdCategoria(detalle.getIdCategoria());

            DetalleProducto actualizado = detalleProductoService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizado));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar detalle
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar un detalle de producto")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            detalleProductoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
