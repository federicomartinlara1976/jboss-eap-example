package net.bounceme.chronos.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import net.bounceme.chronos.entity.Producto;

public interface ProductoRepository {

	// CRUD básico
	Producto crear(Producto producto);

	Optional<Producto> buscarPorId(Long id);

	List<Producto> listarTodos();

	Producto actualizar(Producto producto);

	void eliminar(Long id);

	// Consultas específicas
	List<Producto> buscarPorNombre(String nombre);

	List<Producto> buscarPorRangoPrecio(BigDecimal min, BigDecimal max);

	List<Producto> buscarProductosConStock();

	long contarProductosActivos();

	// Métodos de negocio
	void actualizarStock(Long productoId, Integer nuevoStock);

}