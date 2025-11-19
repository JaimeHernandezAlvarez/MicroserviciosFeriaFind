package com.servicio.Comision.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.Comision.Model.Comision;
import com.servicio.Comision.Repository.ComisionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComisionService {

    @Autowired
    private ComisionRepository comisionRepository;

    // Listar todas las comisiones
    public List<Comision> findAll() {
        return comisionRepository.findAll();
    }

    // Buscar comisión por ID
    public Comision findById(long id) {
        return comisionRepository.findById(id).get();
    }

    // Guardar comisión
    @SuppressWarnings("null")
    public Comision save(Comision comision) {
        return comisionRepository.save(comision);
    }

    // Eliminar comisión por ID
    public void delete(long id) {
        comisionRepository.deleteById(id);
    }
}
