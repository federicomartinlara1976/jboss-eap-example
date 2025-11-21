package net.bounceme.chronos.service.impl;

import jakarta.ejb.Stateless;
import lombok.extern.jbosslog.JBossLog;
import net.bounceme.chronos.dto.MensajeDTO;
import net.bounceme.chronos.service.HolaMundoEJB;

@Stateless(name = "HolaMundoEJB")
@JBossLog
public class HolaMundoEJBImpl implements HolaMundoEJB {
    
    @Override
	public MensajeDTO saludoEJB() {
        log.info("EJB: Ejecutando saludoEJB");
        
        return MensajeDTO.builder()
        		.mensaje("¡Hola desde EJB Stateless!")
        		.build();
    }
    
    @Override
	public MensajeDTO saludoEJB(String nombre) {
    	log.info("EJB: Ejecutando saludoEJB");
        
        return MensajeDTO.builder()
        		.mensaje(String.format("¡Hola %s desde EJB!", nombre))
        		.build();
    }
}