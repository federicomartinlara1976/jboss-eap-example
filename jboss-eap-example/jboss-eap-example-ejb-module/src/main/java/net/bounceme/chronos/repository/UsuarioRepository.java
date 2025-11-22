package net.bounceme.chronos.repository;

import java.util.List;
import java.util.Optional;

import net.bounceme.chronos.entity.Usuario;

public interface UsuarioRepository {

	// Create
	Usuario crear(Usuario usuario);

	// Read
	Optional<Usuario> buscarPorId(Long id);

	List<Usuario> listarTodos();

	Optional<Usuario> buscarPorEmail(String email);

	List<Usuario> buscarPorNombre(String nombre);

	// Update
	Usuario actualizar(Usuario usuario);

	// Delete
	void eliminar(Long id);

	// Métodos específicos de negocio
	Long contarUsuariosActivos();

	List<Usuario> listarUsuariosActivos();

}