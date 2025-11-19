package com.servicio.Reporte.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.servicio.Reporte.Assembler.ReporteModelAssembler;
import com.servicio.Reporte.Model.Reporte;
import com.servicio.Reporte.Service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reporte Controller", description = "Controlador de reportes con soporte HATEOAS")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporteModelAssembler assembler;

    // ------------------------------------------------------------
    // GET - Listar todos los reportes
    // ------------------------------------------------------------
    @Operation(summary = "Listar todos los reportes", description = "Devuelve una lista de todos los reportes con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Reporte>>> listar() {
        List<Reporte> reportes = reporteService.findAll();

        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Reporte>> reportesModel = reportes.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        @SuppressWarnings("null")
        CollectionModel<EntityModel<Reporte>> collectionModel = CollectionModel.of(
                reportesModel,
                linkTo(methodOn(ReporteController.class).listar()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    // ------------------------------------------------------------
    // POST - Crear un nuevo reporte
    // ------------------------------------------------------------
    @Operation(summary = "Guardar nuevo reporte", description = "Crea un registro de reporte con enlaces HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<Reporte>> guardar(@RequestBody Reporte reporte) {
        Reporte nuevo = reporteService.save(reporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevo));
    }

    // ------------------------------------------------------------
    // GET - Buscar reporte por ID
    // ------------------------------------------------------------
    @Operation(summary = "Buscar reporte por ID", description = "Devuelve un reporte específico con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Reporte>> buscar(@PathVariable Integer id) {
        try {
            Reporte reporte = reporteService.findById(id);
            return ResponseEntity.ok(assembler.toModel(reporte));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PUT - Actualizar reporte existente
    // ------------------------------------------------------------
    @Operation(summary = "Actualizar un reporte", description = "Modifica los datos de un reporte existente")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Reporte>> actualizar(@PathVariable Integer id, @RequestBody Reporte reporte) {
        try {
            Reporte existente = reporteService.findById(id);

            existente.setTipoReporte(reporte.getTipoReporte());
            existente.setComentario(reporte.getComentario());
            existente.setEstado(reporte.getEstado());
            existente.setFecha(reporte.getFecha());
            existente.setIdUsuario(reporte.getIdUsuario());
            existente.setIdVendedor(reporte.getIdVendedor());

            Reporte actualizado = reporteService.save(existente);

            return ResponseEntity.ok(assembler.toModel(actualizado));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE - Eliminar un reporte
    // ------------------------------------------------------------
    @Operation(summary = "Eliminar reporte", description = "Elimina un reporte por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            reporteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
