package net.bounceme.chronos.bean;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import net.bounceme.chronos.ejb.HolaMundoEJB;

@Named
@RequestScoped
public class UsuarioBean {
	
	private static final Logger logger = Logger.getLogger(UsuarioBean.class);
    
	@Getter
	@Setter
    private String nombre;
    
    @Getter
    private String mensaje;
    
    @Inject
    @Getter
    private HolaMundoEJB holaMundoEJB;
    
    public void saludar() {
    	logger.infof("=== INICIO saludar() ===");
        logger.infof("Nombre recibido: '%s'", nombre);
        logger.infof("EJB inyectado: %s", holaMundoEJB != null ? "SÍ" : "NO");
    	
        if (nombre != null && !nombre.trim().isEmpty()) {
            mensaje = holaMundoEJB.saludoPersonalizadoEJB(nombre);
        } else {
            mensaje = holaMundoEJB.saludoEJB();
        }
        
        logger.infof("Mensaje final: '%s'", mensaje);
        logger.infof("=== FIN saludar() ===");
    }
}