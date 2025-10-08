package com.service.Feria.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.Feria.Model.Feria;
import com.service.Feria.Repository.FeriaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FeriaService {
    @Autowired
    private FeriaRepository feriaRepository;

    //Listar Ferias
    public List<Feria> findAll(){
        return feriaRepository.findAll();
    }

    //Listar animal por id
    public Feria findById(long id){
        return feriaRepository.findById(id).get();
    }

    //Guardar Animal
    public Feria save(Feria feria){
        return feriaRepository.save(feria);
    }

    //Eliminar animal (por id)
    public void delete(long id){
        feriaRepository.deleteById(id);
    }
}
