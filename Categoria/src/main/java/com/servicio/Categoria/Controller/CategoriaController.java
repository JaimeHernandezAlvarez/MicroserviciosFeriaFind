package com.servicio.Categoria.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.Categoria.Assembler.CategoriaModelAssembler;
import com.servicio.Categoria.Model.Categoria;
import com.servicio.Categoria.Service.CategoriaService;
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
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categoria Controller", description = "Controlador básico de categorías con soporte HATEOAS")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaModelAssembler assembler;

    // ------------------------------------------------------------
    // GET - Listar todas las categorías
    // ------------------------------------------------------------
    @SuppressWarnings("null")
    @Operation(summary = "Listar todas las categorías", description = "Devuelve una lista de todas las categorías con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Categoria>>> listar() {

        List<Categoria> categorias = categoriaService.findAll();

        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Categoria>> categoriasModel = categorias.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Categoria>> collectionModel = CollectionModel.of(
                categoriasModel,
                linkTo(methodOn(CategoriaController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear una nueva categoría
    // ------------------------------------------------------------
    @Operation(summary = "Guardar nueva categoría", description = "Crea una nueva categoría en la base de datos con enlace HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<Categoria>> guardar(@RequestBody Categoria categoria) {
        Categoria nueva = categoriaService.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nueva));
    }

    // ------------------------------------------------------------
    // GET - Buscar categoría por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar categoría por ID", description = "Devuelve los datos de una categoría con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Categoria>> buscar(@PathVariable Integer id) {

        try {
            Categoria categoria = categoriaService.findById(id);
            return ResponseEntity.ok(assembler.toModel(categoria));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar categoría existente
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar categoría", description = "Modifica los datos de una categoría existente con enlace HATEOAS")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Categoria>> actualizar(@PathVariable Integer id, @RequestBody Categoria categoria) {

        try {
            Categoria existente = categoriaService.findById(id);
            existente.setNombreCategoria(categoria.getNombreCategoria());

            Categoria actualizada = categoriaService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizada));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar categoría
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría de la base de datos por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {

        try {
            categoriaService.delete(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
