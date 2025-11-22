package net.bounceme.chronos.repository.impl;

import net.bounceme.chronos.entity.Producto;
import net.bounceme.chronos.repository.ProductoRepository;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Stateless(name = "ProductoRepository")
public class ProductoRepositoryImpl implements ProductoRepository {

    @PersistenceContext(unitName = "miPU")
    private EntityManager em;

    // CRUD básico
    @Override
	public Producto crear(Producto producto) {
        em.persist(producto);
        return producto;
    }

    @Override
	public Optional<Producto> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(Producto.class, id));
    }

    @Override
	public List<Producto> listarTodos() {
        return em.createQuery("SELECT p FROM Producto p ORDER BY p.nombre", Producto.class)
                .getResultList();
    }

    @Override
	public Producto actualizar(Producto producto) {
        return em.merge(producto);
    }

    @Override
	public void eliminar(Long id) {
        buscarPorId(id).ifPresent(producto -> em.remove(producto));
    }

    // Consultas específicas
    @Override
	public List<Producto> buscarPorNombre(String nombre) {
        TypedQuery<Producto> query = em.createQuery(
            "SELECT p FROM Producto p WHERE p.nombre LIKE :nombre ORDER BY p.nombre", Producto.class);
        query.setParameter("nombre", "%" + nombre + "%");
        return query.getResultList();
    }

    @Override
	public List<Producto> buscarPorRangoPrecio(BigDecimal min, BigDecimal max) {
        TypedQuery<Producto> query = em.createQuery(
            "SELECT p FROM Producto p WHERE p.precio BETWEEN :min AND :max ORDER BY p.precio", Producto.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        return query.getResultList();
    }

    @Override
	public List<Producto> buscarProductosConStock() {
        return em.createQuery(
            "SELECT p FROM Producto p WHERE p.stock > 0 AND p.activo = true ORDER BY p.nombre", Producto.class)
            .getResultList();
    }

    @Override
	public long contarProductosActivos() {
        return em.createQuery("SELECT COUNT(p) FROM Producto p WHERE p.activo = true", Long.class)
                .getSingleResult();
    }

    // Métodos de negocio
    @Override
	public void actualizarStock(Long productoId, Integer nuevoStock) {
        buscarPorId(productoId).ifPresent(producto -> {
            producto.setStock(nuevoStock);
            em.merge(producto);
        });
    }
}
