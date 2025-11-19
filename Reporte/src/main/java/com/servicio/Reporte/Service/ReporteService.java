package com.servicio.Reporte.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.Reporte.Model.Reporte;
import com.servicio.Reporte.Repository.ReporteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    // Listar Reportes
    public List<Reporte> findAll() {
        return reporteRepository.findAll();
    }

    // Buscar Reporte por ID
    public Reporte findById(long id) {
        return reporteRepository.findById(id).get();
    }

    // Guardar Reporte
    @SuppressWarnings("null")
    public Reporte save(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    // Eliminar Reporte por ID
    public void delete(long id) {
        reporteRepository.deleteById(id);
    }
}
