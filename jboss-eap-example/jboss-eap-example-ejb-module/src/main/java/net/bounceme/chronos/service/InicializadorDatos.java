package net.bounceme.chronos.service;

import java.math.BigDecimal;

import org.jboss.logging.Logger;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.bounceme.chronos.entity.Producto;
import net.bounceme.chronos.entity.Usuario;

@Singleton
@Startup
public class InicializadorDatos {
	
	private static final Logger logger = Logger.getLogger(InicializadorDatos.class);

    @PersistenceContext(unitName = "miPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
        // Datos de prueba para Usuarios
        em.persist(new Usuario("Juan Pérez", "juan@example.com"));
        em.persist(new Usuario("María García", "maria@example.com"));
        em.persist(new Usuario("Carlos López", "carlos@example.com"));

        // Datos de prueba para Productos
        em.persist(new Producto("Laptop Dell", "Laptop empresarial", new BigDecimal("1200.00"), 10));
        em.persist(new Producto("Mouse Inalámbrico", "Mouse ergonómico", new BigDecimal("25.50"), 50));
        em.persist(new Producto("Teclado Mecánico", "Teclado gaming", new BigDecimal("89.99"), 15));

        logger.info("✅ Datos de prueba inicializados");
    }
}