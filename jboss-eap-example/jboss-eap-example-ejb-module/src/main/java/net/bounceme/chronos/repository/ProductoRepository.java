package net.bounceme.chronos.repository;

import net.bounceme.chronos.entity.Producto;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Stateless
public class ProductoRepository {

    @PersistenceContext(unitName = "miPU")
    private EntityManager em;

    // CRUD básico
    public Producto crear(Producto producto) {
        em.persist(producto);
        return producto;
    }

    public Optional<Producto> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(Producto.class, id));
    }

    public List<Producto> listarTodos() {
        return em.createQuery("SELECT p FROM Producto p ORDER BY p.nombre", Producto.class)
                .getResultList();
    }

    public Producto actualizar(Producto producto) {
        return em.merge(producto);
    }

    public void eliminar(Long id) {
        buscarPorId(id).ifPresent(producto -> em.remove(producto));
    }

    // Consultas específicas
    public List<Producto> buscarPorNombre(String nombre) {
        TypedQuery<Producto> query = em.createQuery(
            "SELECT p FROM Producto p WHERE p.nombre LIKE :nombre ORDER BY p.nombre", Producto.class);
        query.setParameter("nombre", "%" + nombre + "%");
        return query.getResultList();
    }

    public List<Producto> buscarPorRangoPrecio(BigDecimal min, BigDecimal max) {
        TypedQuery<Producto> query = em.createQuery(
            "SELECT p FROM Producto p WHERE p.precio BETWEEN :min AND :max ORDER BY p.precio", Producto.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        return query.getResultList();
    }

    public List<Producto> buscarProductosConStock() {
        return em.createQuery(
            "SELECT p FROM Producto p WHERE p.stock > 0 AND p.activo = true ORDER BY p.nombre", Producto.class)
            .getResultList();
    }

    public long contarProductosActivos() {
        return em.createQuery("SELECT COUNT(p) FROM Producto p WHERE p.activo = true", Long.class)
                .getSingleResult();
    }

    // Métodos de negocio
    public void actualizarStock(Long productoId, Integer nuevoStock) {
        buscarPorId(productoId).ifPresent(producto -> {
            producto.setStock(nuevoStock);
            em.merge(producto);
        });
    }
}
