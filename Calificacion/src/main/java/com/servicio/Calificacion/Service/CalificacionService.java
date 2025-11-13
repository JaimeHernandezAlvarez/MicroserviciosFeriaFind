package com.servicio.Calificacion.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.Calificacion.Model.Calificacion;
import com.servicio.Calificacion.Repository.CalificacionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CalificacionService {
    @Autowired
    private CalificacionRepository feriaRepository;

    //Listar Ferias
    public List<Calificacion> findAll(){
        return feriaRepository.findAll();
    }

    //Listar animal por id
    public Calificacion findById(long id){
        return feriaRepository.findById(id).get();
    }

    //Guardar Animal
    @SuppressWarnings("null")
    public Calificacion save(Calificacion calificacion){
        return feriaRepository.save(calificacion);
    }

    //Eliminar animal (por id)
    public void delete(long id){
        feriaRepository.deleteById(id);
    }
}

