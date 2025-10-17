package net.bounceme.chronos.ejb;

import jakarta.ejb.Stateless;
import org.jboss.logging.Logger;

@Stateless
public class HolaMundoEJB {
    
    private static final Logger logger = Logger.getLogger(HolaMundoEJB.class);
    
    public String saludoEJB() {
        logger.info("EJB: Ejecutando saludoEJB");
        return "¡Hola desde EJB Stateless!";
    }
    
    public String saludoPersonalizadoEJB(String nombre) {
    	logger.info("EJB: Ejecutando saludoPersonalizadoEJB");
        return "¡Hola " + nombre + " desde EJB!";
    }
}