package com.servicio.DetalleTransaccion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.DetalleTransaccion.Model.DetalleTransaccion;

@Repository
public interface DetalleTransaccionRepository extends JpaRepository<DetalleTransaccion,Long>{
    
}
