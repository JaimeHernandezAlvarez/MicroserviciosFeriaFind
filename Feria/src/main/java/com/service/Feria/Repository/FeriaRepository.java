package com.service.Feria.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.Feria.Model.Feria;

@Repository
public interface FeriaRepository extends JpaRepository<Feria,Long>{

}
