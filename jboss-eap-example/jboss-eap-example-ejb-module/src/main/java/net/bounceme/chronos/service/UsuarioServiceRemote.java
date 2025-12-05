package net.bounceme.chronos.service;

import java.util.List;
import java.util.Optional;

import jakarta.ejb.Remote;
import net.bounceme.chronos.entity.Usuario;

@Remote
public interface UsuarioServiceRemote {

	Usuario registrarUsuario(String nombre, String email);

	Optional<Usuario> obtenerUsuario(Long id);

	List<Usuario> listarUsuarios();

	List<Usuario> buscarUsuarios(String criterio);

	Usuario actualizarUsuario(Long id, String nuevoNombre, String nuevoEmail);

	void desactivarUsuario(Long id);

	Long obtenerEstadisticas();

}