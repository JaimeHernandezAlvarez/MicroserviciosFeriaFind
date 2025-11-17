package com.servicio.DetalleProducto.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.DetalleProducto.Model.DetalleProducto;
import com.servicio.DetalleProducto.Repository.DetalleProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalleProductoService {
    @Autowired
    private DetalleProductoRepository detalleProductoRepository;

    //Listar Ferias
    public List<DetalleProducto> findAll(){
        return detalleProductoRepository.findAll();
    }

    //Listar animal por id
    public DetalleProducto findById(long id){
        return detalleProductoRepository.findById(id).get();
    }

    //Guardar Animal
    @SuppressWarnings("null")
    public DetalleProducto save(DetalleProducto feria){
        return detalleProductoRepository.save(feria);
    }

    //Eliminar animal (por id)
    public void delete(long id){
        detalleProductoRepository.deleteById(id);
    }
}
