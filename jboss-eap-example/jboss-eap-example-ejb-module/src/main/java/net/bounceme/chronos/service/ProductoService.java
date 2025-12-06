package net.bounceme.chronos.service;

import java.math.BigDecimal;
import java.util.List;

import jakarta.ejb.Remote;
import net.bounceme.chronos.entity.Producto;

@Remote
public interface ProductoService {

	Producto crearProducto(String nombre, String categoria, BigDecimal precio);

	Producto buscarProducto(Long id);

	List<Producto> buscarPorCategoria(String categoria);

	void clearCache();

}