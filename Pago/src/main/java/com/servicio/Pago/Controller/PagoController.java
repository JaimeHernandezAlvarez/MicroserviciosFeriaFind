package com.servicio.Pago.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.Pago.Assembler.PagoModelAssembler;
import com.servicio.Pago.Model.Pago;
import com.servicio.Pago.Service.PagoService;

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
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pago Controller", description = "Controlador básico de pagos con soporte HATEOAS")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoModelAssembler assembler;

    // ------------------------------------------------------------
    // GET - Listar todos los pagos
    // ------------------------------------------------------------
    @SuppressWarnings("null")
    @Operation(summary = "Listar todos los pagos", description = "Devuelve una lista de todos los pagos con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pago>>> listar() {
        List<Pago> pagos = pagoService.findAll();

        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Pago>> pagosModel = pagos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Pago>> collectionModel = CollectionModel.of(
                pagosModel,
                linkTo(methodOn(PagoController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear un pago
    // ------------------------------------------------------------
    @Operation(summary = "Guardar nuevo pago", description = "Crea un nuevo pago con enlace HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<Pago>> guardar(@RequestBody Pago pago) {
        Pago nuevo = pagoService.save(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    // ------------------------------------------------------------
    // GET - Buscar pago por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar pago por ID", description = "Devuelve los datos de un pago con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pago>> buscar(@PathVariable Integer id) {
        try {
            Pago pago = pagoService.findById(id);
            return ResponseEntity.ok(assembler.toModel(pago));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar pago existente
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar un pago", description = "Modifica los datos de un pago existente con enlace HATEOAS")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Pago>> actualizar(@PathVariable Integer id, @RequestBody Pago pago) {
        try {
            Pago existente = pagoService.findById(id);

            existente.setMetodo(pago.getMetodo());
            existente.setEstado(pago.getEstado());
            existente.setFechaPago(pago.getFechaPago());
            existente.setReferenciaPasarela(pago.getReferenciaPasarela());

            Pago actualizado = pagoService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizado));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar pago
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar pago", description = "Elimina un pago por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            pagoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
