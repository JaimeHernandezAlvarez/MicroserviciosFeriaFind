package com.servicio.Pago.Assembler;

import com.servicio.Pago.Controller.PagoController;
import com.servicio.Pago.Model.Pago;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Convierte entidades Pago en EntityModel<Pago> con enlaces HATEOAS.
 * Esta clase centraliza la lógica de construcción de hipervínculos.
 */
@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(
            pago,
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PagoController.class).buscar(pago.getIdPago())
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PagoController.class).listar()
            ).withRel("pagos")
        );
    }
}
