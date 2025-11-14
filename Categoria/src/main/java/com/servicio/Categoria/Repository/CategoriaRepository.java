package com.servicio.Categoria.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.Categoria.Model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long>{

}
