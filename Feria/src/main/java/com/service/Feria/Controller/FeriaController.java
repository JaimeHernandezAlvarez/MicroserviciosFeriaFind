package com.service.Feria.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.Feria.Model.Feria;
import com.service.Feria.Service.FeriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/ferias")
@Tag(name = "Feria Controller", description = "Controlador básico de ferias")
public class FeriaController {

    @Autowired
    private FeriaService feriaService;

    // ------------------------------------------------------------
    // GET - Listar todas las ferias
    // ------------------------------------------------------------
    @Operation(summary = "Listar todas las ferias", description = "Devuelve una lista de todas las ferias")
    @GetMapping
    public ResponseEntity<List<Feria>> listar() {
        List<Feria> ferias = feriaService.findAll();
        if (ferias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ferias);
    }

    // ------------------------------------------------------------
    // POST - Crear una nueva feria
    // ------------------------------------------------------------
    @Operation(
        summary = "Guardar nueva feria",
        description = "Crea una nueva feria en la base de datos",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la feria a crear (sin ID, ya que es autogenerado)",
            required = true
        )
    )
    @PostMapping
    public ResponseEntity<Feria> guardar(@RequestBody Feria feria) {
        Feria nueva = feriaService.save(feria);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // ------------------------------------------------------------
    // GET - Buscar feria por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar feria por ID", description = "Devuelve los datos de una feria según su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Feria> buscar(@PathVariable Integer id) {
        try {
            Feria feria = feriaService.findById(id);
            return ResponseEntity.ok(feria);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar feria existente
    // ------------------------------------------------------------
    @Operation(
        summary = "Actualizar una feria",
        description = "Modifica los datos de una feria existente",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados de la feria (solo campos modificables)",
            required = true
        )
    )
    @PutMapping("/{id}")
    public ResponseEntity<Feria> actualizar(@PathVariable Integer id, @RequestBody Feria feria) {
        try {
            Feria existente = feriaService.findById(id);
            existente.setNombre(feria.getNombre());
            // Agrega aquí más setters si tu entidad tiene más campos actualizables
            Feria actualizada = feriaService.save(existente);
            return ResponseEntity.ok(actualizada);
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
