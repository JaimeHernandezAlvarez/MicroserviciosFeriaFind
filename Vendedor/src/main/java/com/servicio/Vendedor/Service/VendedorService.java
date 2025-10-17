package com.servicio.Vendedor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.Vendedor.Model.Vendedor;
import com.servicio.Vendedor.Repository.VendedorRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VendedorService {
    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> findAll() {
        return vendedorRepository.findAll();
    }

    //Listar animal por id
    public Vendedor findById(long id){
        return vendedorRepository.findById(id).get();
    }

    /**
     * Guarda una nueva ubicación o actualiza una existente.
     * @param ubicacion La entidad Ubicacion a guardar.
     * @return La entidad Ubicacion guardada.
     */
    public Vendedor save(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    /**
     * Elimina una ubicación de la base de datos por su ID.
     * @param id El ID de la ubicación a eliminar.
     */
    public void delete(Long id) {
        // Primero verificamos si existe para poder lanzar una excepción si no
        vendedorRepository.deleteById(id);
    }   
}
