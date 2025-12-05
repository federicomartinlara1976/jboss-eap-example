package net.bounceme.chronos.service;

import jakarta.ejb.Remote;
import net.bounceme.chronos.dto.MensajeDTO;

@Remote
public interface HolaMundoEJBRemote {

	MensajeDTO saludoEJB();

	MensajeDTO saludoEJB(String nombre);

}