package net.bounceme.chronos.service;

import net.bounceme.chronos.entity.Usuario;
import net.bounceme.chronos.repository.UsuarioRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class UsuarioService {

    @Inject
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(String nombre, String email) {
        // Validar que el email no existe
        if (usuarioRepository.buscarPorEmail(email).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado: " + email);
        }

        Usuario usuario = new Usuario(nombre, email);
        return usuarioRepository.crear(usuario);
    }

    public Optional<Usuario> obtenerUsuario(Long id) {
        return usuarioRepository.buscarPorId(id);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.listarTodos();
    }

    public List<Usuario> buscarUsuarios(String criterio) {
        return usuarioRepository.buscarPorNombre(criterio);
    }

    public Usuario actualizarUsuario(Long id, String nuevoNombre, String nuevoEmail) {
        return usuarioRepository.buscarPorId(id)
                .map(usuario -> {
                    usuario.setNombre(nuevoNombre);
                    usuario.setEmail(nuevoEmail);
                    return usuarioRepository.actualizar(usuario);
                })
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
    }

    public void desactivarUsuario(Long id) {
        usuarioRepository.buscarPorId(id).ifPresent(usuario -> {
            usuario.setActivo(false);
            usuarioRepository.actualizar(usuario);
        });
    }

    public long obtenerEstadisticas() {
        return usuarioRepository.contarUsuariosActivos();
    }
}