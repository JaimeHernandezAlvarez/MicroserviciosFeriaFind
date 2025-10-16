package com.servicio.Usuario.Assembler; // Asegúrate de que este sea el paquete correcto

import com.servicio.Usuario.Controller.UsuarioController; // 1. Importa el controlador de Usuario
import com.servicio.Usuario.Model.Usuario;                  // 2. Importa el modelo de Usuario
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Convierte entidades Usuario en EntityModel<Usuario> con enlaces HATEOAS.
 * Esta clase centraliza la lógica de construcción de hipervínculos para el recurso Usuario.
 */
@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) { // 3. El método ahora recibe un objeto Usuario
        return EntityModel.of(
            usuario,
            // 4. Enlace "self": apunta al método para buscar un usuario por su ID
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscar(usuario.getIdUsuario())).withSelfRel(),
            
            // 5. Enlace "collection": apunta al método que lista todos los usuarios
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listar()).withRel("usuarios")
        );
    }
}