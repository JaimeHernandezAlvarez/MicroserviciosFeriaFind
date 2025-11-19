package com.servicio.Pago.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.Pago.Model.Pago;
import com.servicio.Pago.Repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    // Listar Pagos
    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    // Buscar pago por id
    public Pago findById(long id) {
        return pagoRepository.findById(id).get();
    }

    // Guardar Pago
    @SuppressWarnings("null")
    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    // Eliminar Pago por id
    public void delete(long id) {
        pagoRepository.deleteById(id);
    }
}
