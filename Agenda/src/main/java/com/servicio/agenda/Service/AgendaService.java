package com.servicio.agenda.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.agenda.Model.Agenda;
import com.servicio.agenda.Repository.AgendaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    //Listar Ferias
    public List<Agenda> findAll(){
        return agendaRepository.findAll();
    }

    //Listar animal por id
    public Agenda findById(long id){
        return agendaRepository.findById(id).get();
    }

    //Guardar Animal
    @SuppressWarnings("null")
    public Agenda save(Agenda agenda){
        return agendaRepository.save(agenda);
    }

    //Eliminar animal (por id)
    public void delete(long id){
        agendaRepository.deleteById(id);
    }
}