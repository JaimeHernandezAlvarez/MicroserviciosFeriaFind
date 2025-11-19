package com.servicio.Pago.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicio.Pago.Model.Pago;

public interface PagoRepository extends JpaRepository<Pago,Long>{
    
}
