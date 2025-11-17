package com.servicio.DetalleProducto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicio.DetalleProducto.Model.DetalleProducto;

public interface DetalleProductoRepository extends JpaRepository<DetalleProducto,Long> {

    
}