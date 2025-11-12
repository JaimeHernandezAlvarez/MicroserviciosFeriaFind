package com.servicio.DetalleUsuario.Assembler;

import com.servicio.DetalleUsuario.Controller.DetalleUsuarioController;
import com.servicio.DetalleUsuario.Model.DetalleUsuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component; 

/**
 * Convierte entidades DetalleUsuario en EntityModel<DetalleUsuario> con enlaces HATEOAS.
 * Esta clase centraliza la lógica de construcción de hipervínculos.
 */
@Component
public class DetalleUsuarioModelAssembler implements RepresentationModelAssembler<DetalleUsuario, EntityModel<DetalleUsuario>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<DetalleUsuario> toModel(DetalleUsuario detalleUsuario) {
        return EntityModel.of(
            detalleUsuario,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DetalleUsuarioController.class).buscar(detalleUsuario.getIdDetalle())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DetalleUsuarioController.class).listar()).withRel("detallesUsuarios")
        );
    }
}
