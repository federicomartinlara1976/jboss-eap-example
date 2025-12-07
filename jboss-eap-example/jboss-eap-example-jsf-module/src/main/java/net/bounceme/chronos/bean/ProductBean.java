package net.bounceme.chronos.bean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import net.bounceme.chronos.dto.ProductoDTO;
import net.bounceme.chronos.exceptions.ServiceException;
import net.bounceme.chronos.service.ProductoService;
import net.bounceme.chronos.utils.JsfUtils;

@Named
@RequestScoped
public class ProductBean {

	@Getter
	@Setter
	private ProductoDTO producto;
	
	@Getter
	private List<ProductoDTO> productos;

	@Inject
	@Getter
	private ProductoService productoService;
	
	@PostConstruct
	public void init() {
		producto = new ProductoDTO();
	}

	public void nuevo() {
		try {
			productoService.crearProducto(producto.getNombre(), producto.getCategoria(), producto.getPrecio());
			JsfUtils.writeMessage(FacesMessage.SEVERITY_INFO, "Creado", "El producto se ha creado correctamente");
			
			producto = new ProductoDTO();
		} catch (ServiceException e) {
			JsfUtils.writeMessage(FacesMessage.SEVERITY_ERROR, "Error al crear", e.getMessage());	
		}
	}
	
	public void listar() {
		if (StringUtils.isBlank(producto.getCategoria())) {
			JsfUtils.writeMessage(FacesMessage.SEVERITY_ERROR, "Error al listar", "Escriba una categoría");
			return;
		}
		
		productos = productoService.buscarPorCategoria(producto.getCategoria());
	}
	
	public void clearCache() {
		productoService.clearCache();
		
		JsfUtils.writeMessage(FacesMessage.SEVERITY_INFO, "Limpiado", "Caché borrada correctamente");
	}
	
}