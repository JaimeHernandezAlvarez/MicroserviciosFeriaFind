package com.servicio.agenda.Assembler;

import com.servicio.agenda.Controller.AgendaController;
import com.servicio.agenda.Model.Agenda;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Convierte entidades Agenda en EntityModel<Agenda> con enlaces HATEOAS.
 * Esta clase centraliza la lógica de construcción de hipervínculos.
 */
@Component
public class AgendaModelAssembler implements RepresentationModelAssembler<Agenda, EntityModel<Agenda>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Agenda> toModel(Agenda agenda) {
        return EntityModel.of(
            agenda,
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(AgendaController.class).buscar(agenda.getIdAgenda())
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(AgendaController.class).listar()
            ).withRel("agendas")
        );
    }
}
