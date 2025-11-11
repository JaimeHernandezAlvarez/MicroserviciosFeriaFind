package com.service.Feria.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.service.Feria.Assembler.FeriaModelAssembler;
import com.service.Feria.Model.Feria;
import com.service.Feria.Service.FeriaService;
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
@RequestMapping("/api/v1/ferias")
@Tag(name = "Feria Controller", description = "Controlador básico de ferias con soporte HATEOAS")
public class FeriaController {

    @Autowired
    private FeriaService feriaService;

    @Autowired
    private FeriaModelAssembler assembler;

    // ------------------------------------------------------------
    // GET - Listar todas las ferias
    // ------------------------------------------------------------
    @SuppressWarnings("null")
    @Operation(summary = "Listar todas las ferias", description = "Devuelve una lista de todas las ferias con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Feria>>> listar() {
        List<Feria> ferias = feriaService.findAll();

        if (ferias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Feria>> feriasModel = ferias.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Feria>> collectionModel = CollectionModel.of(
                feriasModel,
                linkTo(methodOn(FeriaController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear una nueva feria
    // ------------------------------------------------------------
    @Operation(summary = "Guardar nueva feria", description = "Crea una nueva feria en la base de datos con enlace HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<Feria>> guardar(@RequestBody Feria feria) {
        Feria nueva = feriaService.save(feria);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nueva));
    }

    // ------------------------------------------------------------
    // GET - Buscar feria por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar feria por ID", description = "Devuelve los datos de una feria con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Feria>> buscar(@PathVariable Integer id) {
        try {
            Feria feria = feriaService.findById(id);
            return ResponseEntity.ok(assembler.toModel(feria));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar feria existente
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar una feria", description = "Modifica los datos de una feria existente con enlace HATEOAS")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Feria>> actualizar(@PathVariable Integer id, @RequestBody Feria feria) {
        try {
            Feria existente = feriaService.findById(id);
            existente.setNombre(feria.getNombre());
            Feria actualizada = feriaService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar feria
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar feria", description = "Elimina una feria de la base de datos por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            feriaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
