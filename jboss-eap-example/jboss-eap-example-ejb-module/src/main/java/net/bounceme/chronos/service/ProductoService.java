package net.bounceme.chronos.service;

import java.math.BigDecimal;
import java.util.List;

import net.bounceme.chronos.entity.Producto;

public interface ProductoService {

	Producto crearProducto(String nombre, String categoria, BigDecimal precio);

	Producto buscarProducto(Long id);

	List<Producto> buscarPorCategoria(String categoria);

	void clearCache();

}