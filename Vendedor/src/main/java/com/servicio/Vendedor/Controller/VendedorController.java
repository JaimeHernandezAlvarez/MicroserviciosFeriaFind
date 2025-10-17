package com.servicio.Vendedor.Controller; // Paquete actualizado a "servicio"

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.Vendedor.Assembler.VendedorModelAssembler;
import com.servicio.Vendedor.Model.Vendedor;
import com.servicio.Vendedor.Service.VendedorService; // Se necesita el servicio de Vendedor
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
@RequestMapping("/api/v1/vendedores") // 1. Se cambia el endpoint base
@Tag(name = "Vendedor Controller", description = "Controlador para la gestión de Vendedores con soporte HATEOAS")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService; // 2. Se inyecta el servicio de Vendedor

    @Autowired
    private VendedorModelAssembler assembler; // 3. Se inyecta el assembler de Vendedor

    // ------------------------------------------------------------
    // GET - Listar todos los vendedores
    // ------------------------------------------------------------
    @Operation(summary = "Listar todos los vendedores", description = "Devuelve una lista de todos los vendedores con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Vendedor>>> listar() {
        List<Vendedor> vendedores = vendedorService.findAll();

        if (vendedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Convierte cada objeto Vendedor a EntityModel usando el assembler
        List<EntityModel<Vendedor>> vendedoresModel = vendedores.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        // Envuelve la colección de EntityModel en un CollectionModel con un enlace "self"
        CollectionModel<EntityModel<Vendedor>> collectionModel = CollectionModel.of(
                vendedoresModel,
                linkTo(methodOn(VendedorController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear un nuevo vendedor
    // ------------------------------------------------------------
    @Operation(summary = "Guardar nuevo vendedor", description = "Crea un nuevo vendedor en la base de datos con enlace HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<Vendedor>> guardar(@RequestBody Vendedor vendedor) {
        Vendedor nuevoVendedor = vendedorService.save(vendedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevoVendedor));
    }

    // ------------------------------------------------------------
    // GET - Buscar vendedor por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar vendedor por ID", description = "Devuelve los datos de un vendedor con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Vendedor>> buscar(@PathVariable Integer id) {
        try {
            Vendedor vendedor = vendedorService.findById(id);
            return ResponseEntity.ok(assembler.toModel(vendedor));
        } catch (Exception e) { // Idealmente, capturar una excepción más específica como ResourceNotFoundException
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar vendedor existente
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar un vendedor", description = "Modifica los datos de un vendedor existente con enlace HATEOAS")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Vendedor>> actualizar(@PathVariable Integer id, @RequestBody Vendedor vendedor) {
        try {
            Vendedor existente = vendedorService.findById(id);
            
            // 4. Se actualizan todos los campos de la entidad Vendedor
            existente.setNombreVendedor(vendedor.getNombreVendedor());
            existente.setDescripcion(vendedor.getDescripcion());
            existente.setEstado(vendedor.getEstado());
            existente.setFotoPerfil(vendedor.getFotoPerfil());
            existente.setIdUsuario(vendedor.getIdUsuario()); // Asumiendo que el ID de usuario se puede cambiar

            Vendedor actualizado = vendedorService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar vendedor
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar vendedor", description = "Elimina un vendedor de la base de datos por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            vendedorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}