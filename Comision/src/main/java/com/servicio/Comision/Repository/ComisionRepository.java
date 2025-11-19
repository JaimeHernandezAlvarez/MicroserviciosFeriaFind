package com.servicio.Comision.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.Comision.Model.Comision;

@Repository
public interface ComisionRepository extends JpaRepository<Comision,Long>{

    
} 