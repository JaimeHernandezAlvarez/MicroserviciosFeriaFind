package com.servicio.Usuario.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.Usuario.Assembler.UsuarioModelAssembler;
import com.servicio.Usuario.Config.JwtService;
import com.servicio.Usuario.Dto.AuthResponse;
import com.servicio.Usuario.Dto.LoginRequest;
import com.servicio.Usuario.Model.Role; // Asegúrate de importar tu Enum
import com.servicio.Usuario.Model.Usuario;
import com.servicio.Usuario.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder; // Corregido import
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuario Controller", description = "Controlador para la gestión de Usuarios con soporte HATEOAS y Seguridad JWT")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    // ------------------------------------------------------------
    // GET - Listar todos los usuarios
    // ------------------------------------------------------------
    @Operation(summary = "Listar todos los usuarios", description = "Devuelve una lista de todos los usuarios")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> listar() {
        List<Usuario> usuarios = usuarioService.findAll();

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Usuario>> usuariosModel = usuarios.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Usuario>> collectionModel = CollectionModel.of(
                usuariosModel,
                linkTo(methodOn(UsuarioController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Registro de Usuario
    // ------------------------------------------------------------
    @Operation(summary = "Registrar nuevo usuario", description = "Crea un usuario, asigna rol USER y devuelve el Token")
    @PostMapping
    public ResponseEntity<AuthResponse> crear(@RequestBody Usuario usuario) {
        
        // 1. Validaciones básicas
        if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // 2. Asignar ROL por defecto (Importante para tu sistema de privilegios)
        if (usuario.getRol() == null) {
            usuario.setRol(Role.USER);
        }

        // 3. Encriptar contraseña
        String passEncriptada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(passEncriptada);

        // 4. Guardar en BD
        Usuario nuevoUsuario = usuarioService.save(usuario);

        // 5. Generar Token automáticamente (Auto-login al registrarse)
        String jwtToken = jwtService.generateToken(nuevoUsuario);

        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }

    // ------------------------------------------------------------
    // GET - Buscar usuario por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar usuario por ID", description = "Devuelve los datos de un usuario")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> buscar(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            // Validar si findById devuelve null en tu servicio (si no usas Optional)
            if (usuario == null) return ResponseEntity.notFound().build(); 
            
            return ResponseEntity.ok(assembler.toModel(usuario));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar usuario
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar un usuario", description = "Modifica los datos de un usuario existente")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        try {
            Usuario existente = usuarioService.findById(id);

            if (existente == null) {
                return ResponseEntity.notFound().build();
            }
            
            if (usuario.getNombreUsuario() != null) existente.setNombreUsuario(usuario.getNombreUsuario());
            if (usuario.getCorreoElectronico() != null) existente.setCorreoElectronico(usuario.getCorreoElectronico());
            if (usuario.getFoto() != null) existente.setFoto(usuario.getFoto());
            if (usuario.getDescripcion() != null) existente.setDescripcion(usuario.getDescripcion());
            if (usuario.getHorario() != null) existente.setHorario(usuario.getHorario());
            
            // Actualizar contraseña solo si viene informada
            if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
                String passEncriptada = passwordEncoder.encode(usuario.getContrasena());
                existente.setContrasena(passEncriptada);
            }
            
            // NO actualizamos el ROL aquí por seguridad, para evitar que un User se vuelva Admin a sí mismo.

            Usuario actualizado = usuarioService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizado));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar usuario
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            // NOTA DE SEGURIDAD:
            // Al haber quitado la restricción en SecurityConfig, cualquier usuario logueado 
            // podría borrar a cualquier otro si adivina el ID.
            // Lo ideal aquí sería verificar si el usuario que hace la petición es el mismo que el del ID
            // o si es ADMIN. Pero por ahora lo dejamos abierto como pediste.
            
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // POST - Login
    // ------------------------------------------------------------
    @Operation(summary = "Iniciar sesión", description = "Verifica credenciales y devuelve un TOKEN JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        
        // 1. Autenticar con Spring Security
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), 
                request.getPassword()
            )
        );

        // 2. Buscar usuario
        Usuario usuario = usuarioService.buscarPorCorreo(request.getEmail());

        // 3. Generar Token (Incluye el Rol dentro del token gracias a la modificación en JwtService)
        String jwtToken = jwtService.generateToken(usuario);

        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }

    // ------------------------------------------------------------
    // GET - Buscar por Email
    // ------------------------------------------------------------
    @Operation(summary = "Buscar por Email", description = "Obtiene un usuario dado su correo")
    @GetMapping("/buscar") 
    public ResponseEntity<EntityModel<Usuario>> buscarPorEmail(@RequestParam String email) {
        Usuario usuario = usuarioService.buscarPorCorreo(email);
        
        if (usuario != null) {
            return ResponseEntity.ok(assembler.toModel(usuario));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}