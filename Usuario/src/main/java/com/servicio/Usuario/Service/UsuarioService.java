package com.servicio.Usuario.Service;

import java.util.List;

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
        return usuarioRepository.save(usuario);
    }

    //Eliminar animal (por id)
    public void delete(long id){
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarPorCorreo(String correo) {
    // Usamos el repositorio. Si no encuentra nada, retornamos null
    return usuarioRepository.findByCorreoElectronico(correo).orElse(null);
}
}
