package net.bounceme.chronos.service;

import net.bounceme.chronos.dto.MensajeDTO;

public interface HolaMundoEJB {

	MensajeDTO saludoEJB();

	MensajeDTO saludoEJB(String nombre);

}