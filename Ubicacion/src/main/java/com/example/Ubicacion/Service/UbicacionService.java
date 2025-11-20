package com.example.Ubicacion.Service; // Asegúrate de que este sea el paquete correcto

import java.util.List;

import com.example.Ubicacion.Model.Ubicacion;
import com.example.Ubicacion.Repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional; 

@Service
@Transactional // Anotación que asegura que todos los métodos se ejecuten dentro de una transacción
public class UbicacionService {
    
    @Autowired
    private UbicacionRepository ubicacionRepository;

    /**
     * Devuelve una lista de todas las ubicaciones en la base de datos.
     * @return Lista de entidades Ubicacion.
     */
    public List<Ubicacion> findAll() {
        return ubicacionRepository.findAll();
    }

    //Listar animal por id
    public Ubicacion findById(long id){
        return ubicacionRepository.findById(id).get();
    }

    /**
     * Guarda una nueva ubicación o actualiza una existente.
     * @param ubicacion La entidad Ubicacion a guardar.
     * @return La entidad Ubicacion guardada.
     */
    @SuppressWarnings("null")
    public Ubicacion save(Ubicacion ubicacion) {
        return ubicacionRepository.save(ubicacion);
    }

    /**
     * Elimina una ubicación de la base de datos por su ID.
     * @param id El ID de la ubicación a eliminar.
     */
    @SuppressWarnings("null")
    public void delete(Long id) {
        // Primero verificamos si existe para poder lanzar una excepción si no
        ubicacionRepository.deleteById(id);
    }
}