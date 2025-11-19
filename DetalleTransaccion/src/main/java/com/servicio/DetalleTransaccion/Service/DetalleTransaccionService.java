package com.servicio.DetalleTransaccion.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.DetalleTransaccion.Model.DetalleTransaccion;
import com.servicio.DetalleTransaccion.Repository.DetalleTransaccionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleTransaccionService {

    @Autowired
    private DetalleTransaccionRepository detalleTransaccionRepository;

    // Listar todos los Detalles de Transacción
    public List<DetalleTransaccion> findAll() {
        return detalleTransaccionRepository.findAll();
    }

    // Listar Detalle por id
    public DetalleTransaccion findById(long id) {
        return detalleTransaccionRepository.findById(id).get();
    }

    // Guardar DetalleTransaccion
    @SuppressWarnings("null")
    public DetalleTransaccion save(DetalleTransaccion detalle) {
        return detalleTransaccionRepository.save(detalle);
    }

    // Eliminar DetalleTransaccion (por id)
    public void delete(long id) {
        detalleTransaccionRepository.deleteById(id);
    }
}
