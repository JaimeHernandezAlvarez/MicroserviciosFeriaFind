package com.servicio.Vendedor.Assembler; // Paquete actualizado a "servicio"

import com.servicio.Vendedor.Controller.VendedorController; // 1. Importa el controlador de Vendedor
import com.servicio.Vendedor.Model.Vendedor;                  // 2. Importa el modelo de Vendedor
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Convierte entidades Vendedor en EntityModel<Vendedor> con enlaces HATEOAS.
 * Esta clase centraliza la lógica de construcción de hipervínculos para el recurso Vendedor.
 */
@Component
public class VendedorModelAssembler implements RepresentationModelAssembler<Vendedor, EntityModel<Vendedor>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Vendedor> toModel(Vendedor vendedor) { // 3. El método ahora recibe un objeto Vendedor
        return EntityModel.of(
            vendedor,
            // 4. Enlace "self": apunta al método para buscar un vendedor por su ID
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendedorController.class).buscar(vendedor.getIdVendedor())).withSelfRel(),
            
            // 5. Enlace "collection": apunta al método que lista todos los vendedores
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(VendedorController.class).listar()).withRel("vendedores")
        );
    }
}