package com.example.Ubicacion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Ubicacion.Model.Ubicacion;

@Repository

public interface UbicacionRepository extends JpaRepository<Ubicacion,Long>{
}
