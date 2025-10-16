package com.servicio.Usuario.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.Usuario.Model.Usuario;
import com.servicio.Usuario.Repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    //Listar Ferias
    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    //Listar animal por id
    public Usuario findById(long id){
        return usuarioRepository.findById(id).get();
    }

    //Guardar Animal
    public Usuario save(Usuario feria){
        return usuarioRepository.save(feria);
    }

    //Eliminar animal (por id)
    public void delete(long id){
        usuarioRepository.deleteById(id);
    }
}
