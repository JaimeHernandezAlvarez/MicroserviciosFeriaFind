package com.servicio.Calificacion.Assembler;

import com.servicio.Calificacion.Controller.CalificacionController;
import com.servicio.Calificacion.Model.Calificacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Convierte entidades Calificacion en EntityModel<Calificacion> con enlaces HATEOAS.
 * Esta clase centraliza la lógica de construcción de hipervínculos.
 */
@Component
public class CalificacionModelAssembler implements RepresentationModelAssembler<Calificacion, EntityModel<Calificacion>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Calificacion> toModel(Calificacion calificacion) {
        return EntityModel.of(
            calificacion,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CalificacionController.class)
                    .buscar(calificacion.getIdCalificacion())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CalificacionController.class)
                    .listar()).withRel("calificaciones")
        );
    }
}
