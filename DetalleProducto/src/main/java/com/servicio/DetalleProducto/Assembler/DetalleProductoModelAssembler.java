package com.servicio.DetalleProducto.Assembler;

import com.servicio.DetalleProducto.Controller.DetalleProductoController;
import com.servicio.DetalleProducto.Model.DetalleProducto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Convierte entidades DetalleProducto en EntityModel<DetalleProducto> con enlaces HATEOAS.
 */
@Component
public class DetalleProductoModelAssembler implements RepresentationModelAssembler<DetalleProducto, EntityModel<DetalleProducto>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<DetalleProducto> toModel(DetalleProducto detalle) {
        return EntityModel.of(
            detalle,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DetalleProductoController.class).buscar(detalle.getIdDetalle())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DetalleProductoController.class).listar()).withRel("detalles-producto")
        );
    }
}
