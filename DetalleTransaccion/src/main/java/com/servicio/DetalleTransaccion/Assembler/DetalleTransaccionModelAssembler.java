package com.servicio.DetalleTransaccion.Assembler;

import com.servicio.DetalleTransaccion.Controller.DetalleTransaccionController;
import com.servicio.DetalleTransaccion.Model.DetalleTransaccion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Convierte entidades DetalleTransaccion en EntityModel<DetalleTransaccion>
 * con enlaces HATEOAS.
 */
@Component
public class DetalleTransaccionModelAssembler implements RepresentationModelAssembler<DetalleTransaccion, EntityModel<DetalleTransaccion>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<DetalleTransaccion> toModel(DetalleTransaccion detalle) {
        return EntityModel.of(
            detalle,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DetalleTransaccionController.class).buscar(detalle.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DetalleTransaccionController.class).listar()).withRel("detalles")
        );
    }
}
