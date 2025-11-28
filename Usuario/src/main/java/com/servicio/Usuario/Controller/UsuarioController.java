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

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
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
    @Operation(summary = "Actualizar un usuario", description = "Modifica los datos de un usuario existente")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        try {
            Usuario existente = usuarioService.findById(id);

            if (existente == null) {
                return ResponseEntity.notFound().build();
            }
            
            // --- ACTUALIZAR TODOS LOS CAMPOS ---
            
            // 1. Campos básicos (Nombre, Foto, Descripción, Horario)
            // Verificamos que no vengan nulos para no borrar datos accidentalmente
            if (usuario.getNombreUsuario() != null) existente.setNombreUsuario(usuario.getNombreUsuario());
            if (usuario.getFoto() != null) existente.setFoto(usuario.getFoto());
            if (usuario.getDescripcion() != null) existente.setDescripcion(usuario.getDescripcion());
            if (usuario.getHorario() != null) existente.setHorario(usuario.getHorario());

            // 2. Email
            if (usuario.getCorreoElectronico() != null) existente.setCorreoElectronico(usuario.getCorreoElectronico());
            
            // 3. Contraseña (Solo si viene una nueva)
            if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
                // Aquí idealmente deberías encriptarla, pero para tu demo:
                existente.setContrasena(usuario.getContrasena());
            }

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

    // ------------------------------------------------------------
    // POST - Login de usuario (Nuevo Endpoint)
    // ------------------------------------------------------------
    @Operation(summary = "Iniciar sesión", description = "Verifica credenciales y devuelve el usuario si son correctas")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody java.util.Map<String, String> credenciales) {
        // 1. Extraemos los datos que envía Android
        String email = credenciales.get("correoElectronico");
        String passwordIngresada = credenciales.get("password"); // Android envía "password"

        if (email == null || passwordIngresada == null) {
            return ResponseEntity.badRequest().body("Faltan credenciales");
        }

        // 2. Buscamos el usuario en la BD
        Usuario usuario = usuarioService.buscarPorCorreo(email);

        // 3. Verificamos si existe y si la contraseña coincide
        if (usuario != null) {
            // --- AQUÍ ESTÁ LA SOLUCIÓN ---
            // passwordEncoder.matches( textoPlano, hashDeLaBD )
            // El primer argumento es lo que llega de Android ("123456789")
            // El segundo es lo que está en la BD ("$2a$10$...")
            if (passwordEncoder.matches(passwordIngresada, usuario.getContrasena())) {
                return ResponseEntity.ok(assembler.toModel(usuario));
            }
        }

        // 4. Si falló algo (usuario null o contraseña mal), devolvemos 401 Unauthorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Operation(summary = "Buscar por Email", description = "Obtiene un usuario dado su correo electrónico")
    @GetMapping("/buscar") // La url final será: /api/v1/usuarios/buscar?email=ejemplo@correo.com
    public ResponseEntity<EntityModel<Usuario>> buscarPorEmail(@RequestParam String email) {
        Usuario usuario = usuarioService.buscarPorCorreo(email);
        
        if (usuario != null) {
            return ResponseEntity.ok(assembler.toModel(usuario));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}