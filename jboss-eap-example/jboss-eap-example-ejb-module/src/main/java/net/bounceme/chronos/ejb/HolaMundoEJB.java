package net.bounceme.chronos.ejb;

import jakarta.ejb.Stateless;
import lombok.extern.jbosslog.JBossLog;

@Stateless
@JBossLog
public class HolaMundoEJB {
    
    public String saludoEJB() {
        log.info("EJB: Ejecutando saludoEJB");
        return "¡Hola desde EJB Stateless!";
    }
    
    public String saludoPersonalizadoEJB(String nombre) {
    	log.info("EJB: Ejecutando saludoPersonalizadoEJB");
        return String.format("¡Hola %s desde EJB!", nombre);
    }
}