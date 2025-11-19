package com.servicio.Transaccion.Assembler;

import com.servicio.Transaccion.Controller.TransaccionController;
import com.servicio.Transaccion.Model.Transaccion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Convierte entidades Transaccion en EntityModel<Transaccion> con enlaces HATEOAS.
 * Esta clase centraliza la lógica de construcción de hipervínculos.
 */
@Component
public class TransaccionModelAssembler implements RepresentationModelAssembler<Transaccion, EntityModel<Transaccion>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Transaccion> toModel(Transaccion transaccion) {
        return EntityModel.of(
            transaccion,
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(TransaccionController.class)
                        .buscar(transaccion.getIdTransaccion()))
                .withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(TransaccionController.class)
                        .listar())
                .withRel("transacciones")
        );
    }
}
