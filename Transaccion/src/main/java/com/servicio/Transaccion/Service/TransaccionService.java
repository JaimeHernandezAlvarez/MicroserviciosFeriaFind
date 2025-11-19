package com.servicio.Transaccion.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.Transaccion.Model.Transaccion;
import com.servicio.Transaccion.Repository.TransaccionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    // Listar todas las transacciones
    public List<Transaccion> findAll() {
        return transaccionRepository.findAll();
    }

    // Buscar transaccion por id
    public Transaccion findById(long id) {
        return transaccionRepository.findById(id).get();
    }

    // Guardar transaccion
    @SuppressWarnings("null")
    public Transaccion save(Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }

    // Eliminar transaccion por id
    public void delete(long id) {
        transaccionRepository.deleteById(id);
    }
}
