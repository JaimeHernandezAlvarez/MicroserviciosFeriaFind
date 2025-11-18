package com.servicio.Producto.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.Producto.Assembler.ProductoModelAssembler;
import com.servicio.Producto.Model.Producto;
import com.servicio.Producto.Service.ProductoService;

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
@RequestMapping("/api/v1/productos")
@Tag(name = "Producto Controller", description = "Controlador básico de productos con soporte HATEOAS")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    // ------------------------------------------------------------
    // GET - Listar todos los productos
    // ------------------------------------------------------------
    @SuppressWarnings("null")
    @Operation(summary = "Listar todos los productos", description = "Devuelve una lista de todos los productos con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> listar() {
        List<Producto> productos = productoService.findAll();

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Producto>> productosModel = productos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Producto>> collectionModel = CollectionModel.of(
                productosModel,
                linkTo(methodOn(ProductoController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear un nuevo producto
    // ------------------------------------------------------------
    @Operation(summary = "Guardar nuevo producto", description = "Crea un nuevo producto en la base de datos con enlace HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<Producto>> guardar(@RequestBody Producto producto) {
        Producto nuevo = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    // ------------------------------------------------------------
    // GET - Buscar producto por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar producto por ID", description = "Devuelve los datos de un producto con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> buscar(@PathVariable Integer id) {
        try {
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(assembler.toModel(producto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar producto existente
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar un producto", description = "Modifica los datos de un producto existente con enlace HATEOAS")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> actualizar(@PathVariable Integer id, @RequestBody Producto producto) {
        try {
            Producto existente = productoService.findById(id);

            existente.setNombre(producto.getNombre());
            existente.setPrecio(producto.getPrecio());
            existente.setUnidadMedida(producto.getUnidadMedida());
            existente.setImagen(producto.getImagen());
            existente.setIdVendedor(producto.getIdVendedor());
            existente.setIdCategoria(producto.getIdCategoria());

            Producto actualizado = productoService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar producto
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar producto", description = "Elimina un producto de la base de datos por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
