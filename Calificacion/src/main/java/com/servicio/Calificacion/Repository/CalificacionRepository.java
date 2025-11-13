package com.servicio.Calificacion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.Calificacion.Model.Calificacion;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion,Long>{

}
