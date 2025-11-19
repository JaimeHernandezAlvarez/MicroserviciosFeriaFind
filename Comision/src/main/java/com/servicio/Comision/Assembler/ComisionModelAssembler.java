package com.servicio.Comision.Assembler;

import com.servicio.Comision.Controller.ComisionController;
import com.servicio.Comision.Model.Comision;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Convierte entidades Comision en EntityModel<Comision> con enlaces HATEOAS.
 */
@Component
public class ComisionModelAssembler implements RepresentationModelAssembler<Comision, EntityModel<Comision>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Comision> toModel(Comision comision) {
        return EntityModel.of(
            comision,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComisionController.class).buscar(comision.getIdComision())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ComisionController.class).listar()).withRel("comisiones")
        );
    }
}
