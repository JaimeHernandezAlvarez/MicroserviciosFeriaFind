package com.service.Feria.Assembler;

import com.service.Feria.Controller.FeriaController;
import com.service.Feria.Model.Feria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Convierte entidades Feria en EntityModel<Feria> con enlaces HATEOAS.
 * Esta clase centraliza la lógica de construcción de hipervínculos.
 */
@Component
public class FeriaModelAssembler implements RepresentationModelAssembler<Feria, EntityModel<Feria>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Feria> toModel(Feria feria) {
        return EntityModel.of(
            feria,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeriaController.class).buscar(feria.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FeriaController.class).listar()).withRel("ferias")
        );
    }
}
