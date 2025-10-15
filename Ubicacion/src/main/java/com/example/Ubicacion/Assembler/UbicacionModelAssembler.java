package com.example.Ubicacion.Assembler; // Asegúrate de que este sea el paquete correcto

import com.example.Ubicacion.Controller.UbicacionController; // 1. Importa el controlador correcto
import com.example.Ubicacion.Model.Ubicacion;                  // 2. Importa el modelo correcto
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

/**
 * Convierte entidades Ubicacion en EntityModel<Ubicacion> con enlaces HATEOAS.
 * Esta clase centraliza la lógica de construcción de hipervínculos para el recurso Ubicacion.
 */
@Component
public class UbicacionModelAssembler implements RepresentationModelAssembler<Ubicacion, EntityModel<Ubicacion>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Ubicacion> toModel(Ubicacion ubicacion) { // 3. El método ahora recibe un objeto Ubicacion
        return EntityModel.of(
            ubicacion,
            // 4. Enlace "self": apunta al método para buscar una ubicación por su ID
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UbicacionController.class).buscar(ubicacion.getIdUbicacion())).withSelfRel(),
            
            // 5. Enlace "collection": apunta al método que lista todas las ubicaciones
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UbicacionController.class).listar()).withRel("ubicaciones")
        );
    }
}