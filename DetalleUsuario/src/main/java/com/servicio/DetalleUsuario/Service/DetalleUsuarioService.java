package com.servicio.DetalleUsuario.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.DetalleUsuario.Model.DetalleUsuario;
import com.servicio.DetalleUsuario.Repository.DetalleUsuarioRepository;

import jakarta.transaction.Transactional; 

@Service
@Transactional
public class DetalleUsuarioService {

    @Autowired
    private DetalleUsuarioRepository detalleUsuarioRepository;

    // 📋 Listar todos los detalles de usuario
    public List<DetalleUsuario> findAll() {
        return detalleUsuarioRepository.findAll();
    }

    // 🔍 Buscar un detalle por su ID
    public DetalleUsuario findById(long id) {
        return detalleUsuarioRepository.findById(id).orElse(null);
    }

    // 💾 Guardar o actualizar un detalle de usuario
    @SuppressWarnings("null")
    public DetalleUsuario save(DetalleUsuario detalleUsuario) {
        return detalleUsuarioRepository.save(detalleUsuario);
    }

    // 🗑️ Eliminar un detalle por ID
    public void delete(long id) {
        detalleUsuarioRepository.deleteById(id);
    }
}
