package com.example.Ubicacion.Controller; // Asegúrate de que este sea el paquete correcto

import java.util.List;
import java.util.stream.Collectors;

import com.example.Ubicacion.Assembler.UbicacionModelAssembler;
import com.example.Ubicacion.Model.Ubicacion;
import com.example.Ubicacion.Service.UbicacionService; // Se necesita el servicio de Ubicacion
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
@RequestMapping("/api/v1/ubicaciones") // 1. Se cambia el endpoint base
@Tag(name = "Ubicacion Controller", description = "Controlador para la gestión de Ubicaciones con soporte HATEOAS")
public class UbicacionController {

    @Autowired
    private UbicacionService ubicacionService; // 2. Se inyecta el servicio de Ubicacion

    @Autowired
    private UbicacionModelAssembler assembler; // 3. Se inyecta el assembler de Ubicacion

    // ------------------------------------------------------------
    // GET - Listar todas las ubicaciones
    // ------------------------------------------------------------
    @SuppressWarnings("null")
    @Operation(summary = "Listar todas las ubicaciones", description = "Devuelve una lista de todas las ubicaciones con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Ubicacion>>> listar() {
        List<Ubicacion> ubicaciones = ubicacionService.findAll();

        if (ubicaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Convierte cada objeto Ubicacion a EntityModel usando el assembler
        List<EntityModel<Ubicacion>> ubicacionesModel = ubicaciones.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        // Envuelve la colección de EntityModel en un CollectionModel con un enlace "self"
        CollectionModel<EntityModel<Ubicacion>> collectionModel = CollectionModel.of(
                ubicacionesModel,
                linkTo(methodOn(UbicacionController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear una nueva ubicación
    // ------------------------------------------------------------
    @Operation(summary = "Guardar nueva ubicación", description = "Crea una nueva ubicación en la base de datos con enlace HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<Ubicacion>> guardar(@RequestBody Ubicacion ubicacion) {
        Ubicacion nuevaUbicacion = ubicacionService.save(ubicacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevaUbicacion));
    }

    // ------------------------------------------------------------
    // GET - Buscar ubicación por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar ubicación por ID", description = "Devuelve los datos de una ubicación con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Ubicacion>> buscar(@PathVariable Integer id) {
        try {
            Ubicacion ubicacion = ubicacionService.findById(id);
            return ResponseEntity.ok(assembler.toModel(ubicacion));
        } catch (Exception e) { // Idealmente, capturar una excepción más específica como ResourceNotFoundException
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar ubicación existente
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar una ubicación", description = "Modifica los datos de una ubicación existente con enlace HATEOAS")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Ubicacion>> actualizar(@PathVariable Long id, @RequestBody Ubicacion ubicacion) {
        try {
            Ubicacion existente = ubicacionService.findById(id);
            // 4. Se actualizan todos los campos de la entidad
            existente.setNombreFeria(ubicacion.getNombreFeria());
            existente.setDireccion(ubicacion.getDireccion());
            existente.setLatitud(ubicacion.getLatitud());
            existente.setLongitud(ubicacion.getLongitud());

            Ubicacion actualizada = ubicacionService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar ubicación
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar ubicación", description = "Elimina una ubicación de la base de datos por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            ubicacionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    public Object buscar(Long idUbicacion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscar'");
    }
}