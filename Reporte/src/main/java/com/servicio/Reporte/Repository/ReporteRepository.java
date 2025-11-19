package com.servicio.Reporte.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.Reporte.Model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte,Long>{

}
