package com.servicio.Categoria.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.Categoria.Model.Categoria;
import com.servicio.Categoria.Repository.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Listar Categorías
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    // Listar categoría por id
    public Categoria findById(long id) {
        return categoriaRepository.findById(id).get();
    }

    // Guardar categoría
    @SuppressWarnings("null")
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // Eliminar categoría por id
    public void delete(long id) {
        categoriaRepository.deleteById(id);
    }
}
