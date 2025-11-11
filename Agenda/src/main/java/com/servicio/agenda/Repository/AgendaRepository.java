package com.servicio.agenda.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.agenda.Model.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda,Long>{

}
