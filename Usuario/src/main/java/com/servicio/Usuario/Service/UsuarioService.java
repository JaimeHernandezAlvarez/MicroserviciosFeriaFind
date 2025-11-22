package com.servicio.Usuario.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.Usuario.Model.Usuario;
import com.servicio.Usuario.Repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Listar Ferias
    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    //Listar animal por id
    public Usuario findById(long id){
        return usuarioRepository.findById(id).get();
    }

    //Guardar Animal
    public Usuario save(Usuario usuario){
        String passwordEncriptado = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(passwordEncriptado);
        return usuarioRepository.save(usuario);
    }

    //Eliminar animal (por id)
    public void delete(long id){
        usuarioRepository.deleteById(id);
    }
}
