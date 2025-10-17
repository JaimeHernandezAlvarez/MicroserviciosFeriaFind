package com.servicio.Vendedor.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.Vendedor.Model.Vendedor;

@Repository

public interface VendedorRepository extends JpaRepository<Vendedor,Long>{
}
