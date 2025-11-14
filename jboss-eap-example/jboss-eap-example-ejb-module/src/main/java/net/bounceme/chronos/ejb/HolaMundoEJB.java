package net.bounceme.chronos.ejb;

import jakarta.ejb.Stateless;
import lombok.extern.jbosslog.JBossLog;
import net.bounceme.chronos.dto.MensajeDTO;

@Stateless
@JBossLog
public class HolaMundoEJB {
    
    public MensajeDTO saludoEJB() {
        log.info("EJB: Ejecutando saludoEJB");
        
        return MensajeDTO.builder()
        		.mensaje("¡Hola desde EJB Stateless!")
        		.build();
    }
    
    public MensajeDTO saludoEJB(String nombre) {
    	log.info("EJB: Ejecutando saludoEJB");
        
        return MensajeDTO.builder()
        		.mensaje(String.format("¡Hola %s desde EJB!", nombre))
        		.build();
    }
}