package com.servicio.Comision.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.Comision.Assembler.ComisionModelAssembler;
import com.servicio.Comision.Model.Comision;
import com.servicio.Comision.Service.ComisionService;
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
@RequestMapping("/api/v1/comisiones")
@Tag(name = "Comision Controller", description = "Controlador de comisiones con soporte HATEOAS")
public class ComisionController {

    @Autowired
    private ComisionService comisionService;

    @Autowired
    private ComisionModelAssembler assembler;

    // --------------------------------------------------------------------
    // GET - Listar todas las comisiones
    // --------------------------------------------------------------------
    @SuppressWarnings("null")
    @Operation(summary = "Listar comisiones", description = "Devuelve todas las comisiones con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Comision>>> listar() {
        List<Comision> comisiones = comisionService.findAll();

        if (comisiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Comision>> coleccion = comisiones.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Comision>> model = CollectionModel.of(
                coleccion,
                linkTo(methodOn(ComisionController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(model);
    }

    // --------------------------------------------------------------------
    // POST - Guardar nueva comisión
    // --------------------------------------------------------------------
    @Operation(summary = "Guardar comisión", description = "Crea una nueva comisión")
    @PostMapping
    public ResponseEntity<EntityModel<Comision>> guardar(@RequestBody Comision comision) {
        Comision nueva = comisionService.save(comision);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nueva));
    }

    // --------------------------------------------------------------------
    // GET - Buscar por ID
    // --------------------------------------------------------------------
    @Operation(summary = "Buscar comisión por ID", description = "Devuelve una comisión específica con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Comision>> buscar(@PathVariable Integer id) {
        try {
            Comision com = comisionService.findById(id);
            return ResponseEntity.ok(assembler.toModel(com));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --------------------------------------------------------------------
    // PUT - Actualizar comisión
    // --------------------------------------------------------------------
    @Operation(summary = "Actualizar comisión", description = "Modifica los datos de una comisión existente")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Comision>> actualizar(@PathVariable Integer id, @RequestBody Comision comision) {
        try {
            Comision existente = comisionService.findById(id);

            existente.setPorcentaje(comision.getPorcentaje());
            existente.setMontoAplicado(comision.getMontoAplicado());
            existente.setIdTransaccion(comision.getIdTransaccion());

            Comision actualizada = comisionService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizada));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --------------------------------------------------------------------
    // DELETE - Eliminar
    // --------------------------------------------------------------------
    @Operation(summary = "Eliminar comisión", description = "Elimina una comisión por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            comisionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
