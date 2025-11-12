package com.servicio.DetalleUsuario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.DetalleUsuario.Model.DetalleUsuario;

@Repository
public interface DetalleUsuarioRepository extends JpaRepository<DetalleUsuario,Long>{

}
