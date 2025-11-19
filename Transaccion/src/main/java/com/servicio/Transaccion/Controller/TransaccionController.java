package com.servicio.Transaccion.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.Transaccion.Assembler.TransaccionModelAssembler;
import com.servicio.Transaccion.Model.Transaccion;
import com.servicio.Transaccion.Service.TransaccionService;

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
@RequestMapping("/api/v1/transacciones")
@Tag(name = "Transaccion Controller", description = "Controlador básico de transacciones con soporte HATEOAS")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private TransaccionModelAssembler assembler;

    // ------------------------------------------------------------
    // GET - Listar todas las transacciones
    // ------------------------------------------------------------
    @Operation(summary = "Listar todas las transacciones", description = "Devuelve una lista de todas las transacciones con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Transaccion>>> listar() {
        List<Transaccion> transacciones = transaccionService.findAll();

        if (transacciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Transaccion>> transaccionesModel = transacciones.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        @SuppressWarnings("null")
        CollectionModel<EntityModel<Transaccion>> collectionModel = CollectionModel.of(
                transaccionesModel,
                linkTo(methodOn(TransaccionController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear una nueva transacción
    // ------------------------------------------------------------
    @Operation(summary = "Guardar nueva transacción", description = "Crea una nueva transacción en la base de datos con enlace HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<Transaccion>> guardar(@RequestBody Transaccion transaccion) {
        Transaccion nueva = transaccionService.save(transaccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nueva));
    }

    // ------------------------------------------------------------
    // GET - Buscar transacción por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar transacción por ID", description = "Devuelve los datos de una transacción con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Transaccion>> buscar(@PathVariable Integer id) {
        try {
            Transaccion transaccion = transaccionService.findById(id);
            return ResponseEntity.ok(assembler.toModel(transaccion));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar transacción existente
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar una transacción", description = "Modifica los datos de una transacción existente con enlace HATEOAS")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Transaccion>> actualizar(@PathVariable Integer id, @RequestBody Transaccion transaccion) {
        try {
            Transaccion existente = transaccionService.findById(id);

            // Aquí actualizas solo lo que corresponda
            existente.setFecha(transaccion.getFecha());
            existente.setEstado(transaccion.getEstado());
            existente.setMontoTotal(transaccion.getMontoTotal());
            existente.setIdUsuario(transaccion.getIdUsuario());
            existente.setIdVendedor(transaccion.getIdVendedor());
            existente.setIdPago(transaccion.getIdPago());

            Transaccion actualizada = transaccionService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizada));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar transacción
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar transacción", description = "Elimina una transacción de la base de datos por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            transaccionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
