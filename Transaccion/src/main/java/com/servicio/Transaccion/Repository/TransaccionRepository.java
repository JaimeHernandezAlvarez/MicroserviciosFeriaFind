package com.servicio.Transaccion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.Transaccion.Model.Transaccion;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion,Long>{
    
}
