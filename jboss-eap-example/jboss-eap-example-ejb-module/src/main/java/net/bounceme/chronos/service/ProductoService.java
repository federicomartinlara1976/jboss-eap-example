package net.bounceme.chronos.service;

import java.math.BigDecimal;
import java.util.List;

import net.bounceme.chronos.dto.ProductoDTO;

public interface ProductoService {

	ProductoDTO crearProducto(String nombre, String categoria, BigDecimal precio);

	ProductoDTO buscarProducto(Long id);

	List<ProductoDTO> buscarPorCategoria(String categoria);

	void clearCache();

}