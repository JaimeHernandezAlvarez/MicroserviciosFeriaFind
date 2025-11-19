package com.servicio.Reporte.Assembler;

import com.servicio.Reporte.Controller.ReporteController;
import com.servicio.Reporte.Model.Reporte;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Convierte entidades Reporte en EntityModel<Reporte> con enlaces HATEOAS.
 */
@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<Reporte, EntityModel<Reporte>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Reporte> toModel(Reporte reporte) {
        return EntityModel.of(
                reporte,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReporteController.class).buscar(reporte.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReporteController.class).listar()).withRel("reportes")
        );
    }
}
