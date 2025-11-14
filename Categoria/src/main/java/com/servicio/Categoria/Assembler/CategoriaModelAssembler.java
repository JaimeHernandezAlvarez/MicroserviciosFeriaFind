package com.servicio.Categoria.Assembler;

import com.servicio.Categoria.Controller.CategoriaController;
import com.servicio.Categoria.Model.Categoria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Convierte entidades Categoria en EntityModel<Categoria> con enlaces HATEOAS.
 * Esta clase centraliza la lógica de construcción de hipervínculos.
 */
@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<Categoria, EntityModel<Categoria>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Categoria> toModel(Categoria categoria) {
        return EntityModel.of(
            categoria,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoriaController.class).buscar(categoria.getIdCategoria())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoriaController.class).listar()).withRel("categorias")
        );
    }
}
