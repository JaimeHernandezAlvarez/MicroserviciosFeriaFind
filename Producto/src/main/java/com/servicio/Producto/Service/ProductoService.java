package com.servicio.Producto.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.Producto.Model.Producto;
import com.servicio.Producto.Repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // ------------------------------------------------------------
    // Listar Productos
    // ------------------------------------------------------------
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    // ------------------------------------------------------------
    // Buscar producto por ID
    // ------------------------------------------------------------
    public Producto findById(long id) {
        return productoRepository.findById(id).get();
    }

    // ------------------------------------------------------------
    // Guardar producto
    // ------------------------------------------------------------
    @SuppressWarnings("null")
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    // ------------------------------------------------------------
    // Eliminar producto por ID
    // ------------------------------------------------------------
    public void delete(long id) {
        productoRepository.deleteById(id);
    }
}
