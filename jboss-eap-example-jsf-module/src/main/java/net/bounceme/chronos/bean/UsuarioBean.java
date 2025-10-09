package net.bounceme.chronos.bean;

import net.bounceme.chronos.ejb.HolaMundoEJB;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class UsuarioBean {
	
	private static final Logger logger = Logger.getLogger(UsuarioBean.class);
    
    private String nombre;
    private String mensaje;
    
    @Inject
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
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMensaje() { return mensaje; }

	public HolaMundoEJB getHolaMundoEJB() {
		return holaMundoEJB;
	}
}