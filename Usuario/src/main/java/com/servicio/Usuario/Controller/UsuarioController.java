package com.servicio.Usuario.Controller; // Asegúrate de que este sea el paquete correcto

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.Usuario.Assembler.UsuarioModelAssembler;
import com.servicio.Usuario.Model.Usuario;
import com.servicio.Usuario.Service.UsuarioService;
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
@RequestMapping("/api/v1/usuarios") // 1. Endpoint base cambiado
@Tag(name = "Usuario Controller", description = "Controlador para la gestión de Usuarios con soporte HATEOAS")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService; // 2. Inyección del servicio de Usuario

    @Autowired
    private UsuarioModelAssembler assembler; // 3. Inyección del assembler de Usuario

    // ------------------------------------------------------------
    // GET - Listar todos los usuarios
    // ------------------------------------------------------------
    @Operation(summary = "Listar todos los usuarios", description = "Devuelve una lista de todos los usuarios con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> listar() {
        List<Usuario> usuarios = usuarioService.findAll();

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Usuario>> usuariosModel = usuarios.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        @SuppressWarnings("null")
        CollectionModel<EntityModel<Usuario>> collectionModel = CollectionModel.of(
                usuariosModel,
                linkTo(methodOn(UsuarioController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear un nuevo usuario
    // ------------------------------------------------------------
    @Operation(summary = "Guardar nuevo usuario", description = "Crea un nuevo usuario en la base de datos con enlace HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> guardar(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevoUsuario));
    }

    // ------------------------------------------------------------
    // GET - Buscar usuario por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar usuario por ID", description = "Devuelve los datos de un usuario con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> buscar(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(assembler.toModel(usuario));
        } catch (Exception e) { // Idealmente, capturar una excepción más específica como ResourceNotFoundException
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar usuario existente
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar un usuario", description = "Modifica los datos de un usuario existente con enlace HATEOAS")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        try {
            Usuario existente = usuarioService.findById(id);
            
            // 4. Se actualizan los campos de la entidad Usuario
            existente.setCorreoElectronico(usuario.getCorreoElectronico());
            
            // Nota de seguridad: La contraseña debe ser manejada con cuidado.
            // La capa de servicio debería encargarse de verificar si la contraseña
            // ha cambiado y codificarla antes de guardarla.
            existente.setContrasena(usuario.getContrasena());

            Usuario actualizado = usuarioService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar usuario
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario de la base de datos por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}