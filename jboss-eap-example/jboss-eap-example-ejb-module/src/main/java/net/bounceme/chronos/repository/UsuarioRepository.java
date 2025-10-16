package net.bounceme.chronos.repository;

import net.bounceme.chronos.entity.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class UsuarioRepository {

    @PersistenceContext(unitName = "miPU")
    private EntityManager em;

    // Create
    public Usuario crear(Usuario usuario) {
        em.persist(usuario);
        return usuario;
    }

    // Read
    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(Usuario.class, id));
    }

    public List<Usuario> listarTodos() {
        return em.createNamedQuery("Usuario.findAll", Usuario.class)
                .getResultList();
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst();
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findByNombre", Usuario.class);
        query.setParameter("nombre", "%" + nombre + "%");
        return query.getResultList();
    }

    // Update
    public Usuario actualizar(Usuario usuario) {
        return em.merge(usuario);
    }

    // Delete
    public void eliminar(Long id) {
        buscarPorId(id).ifPresent(usuario -> em.remove(usuario));
    }

    // Métodos específicos de negocio
    public Long contarUsuariosActivos() {
        return em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.activo = true", Long.class)
                .getSingleResult();
    }

    public List<Usuario> listarUsuariosActivos() {
        return em.createQuery("SELECT u FROM Usuario u WHERE u.activo = true ORDER BY u.nombre", Usuario.class)
                .getResultList();
    }
}