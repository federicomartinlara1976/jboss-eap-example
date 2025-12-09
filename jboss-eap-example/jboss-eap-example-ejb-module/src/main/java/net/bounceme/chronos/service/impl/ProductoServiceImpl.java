package net.bounceme.chronos.service.impl;

import java.math.BigDecimal;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.jbosslog.JBossLog;
import net.bounceme.chronos.dto.ProductoDTO;
import net.bounceme.chronos.entity.Producto;
import net.bounceme.chronos.service.ProductoService;

@Stateless
@JBossLog
public class ProductoServiceImpl implements ProductoService {
    
	@PersistenceContext
    private EntityManager em;
    
    public ProductoDTO crearProducto(String nombre, String categoria, BigDecimal precio) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setPrecio(precio);
        
        em.persist(producto);
        log.infof("✅ Producto creado: %s", producto);
        
        return new ProductoDTO(producto.getNombre(), producto.getCategoria(), producto.getPrecio());
    }
    
    public ProductoDTO buscarProducto(Long id) {
        long start = System.currentTimeMillis();
        Producto producto = em.find(Producto.class, id);
        long tiempo = System.currentTimeMillis() - start;
        
        log.infof("⏱️ Buscar producto ID %d - Tiempo: %d ms (Cache: %s)", 
                 id, tiempo, em.getEntityManagerFactory().getCache().contains(Producto.class, id));
        
        return new ProductoDTO(producto.getNombre(), producto.getCategoria(), producto.getPrecio());
    }
    
    public List<ProductoDTO> buscarPorCategoria(String categoria) {
        long start = System.currentTimeMillis();
        
        List<Producto> productos = em.createNamedQuery("Producto.findByCategoria", Producto.class)
            .setParameter("categoria", categoria)
            .setHint("org.hibernate.cacheable", true)
            .getResultList();
            
        long tiempo = System.currentTimeMillis() - start;
        log.infof("⏱️ Buscar por categoría '%s' - Tiempo: %d ms - Resultados: %d", 
                 categoria, tiempo, productos.size());
        
        return productos.stream()
        		.map(producto -> {
        			return new ProductoDTO(producto.getNombre(), producto.getCategoria(), producto.getPrecio());
        		})
        		.toList();
    }
    
    public void clearCache() {
        em.getEntityManagerFactory().getCache().evictAll();
        log.info("🧹 Cache de JPA limpiado");
    }
}
