package net.bounceme.chronos.ws;

import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import net.bounceme.chronos.dto.MensajeDTO;
import net.bounceme.chronos.ejb.HolaMundoEJB;

@WebService
public class HolaMundoWS {
    
    @Inject
    private HolaMundoEJB holaMundoEJB;
    
    @WebMethod
    public MensajeDTO saludar() {
        return holaMundoEJB.saludoEJB();
    }
    
    @WebMethod
    public MensajeDTO saludarPersonalizado(String nombre) {
        return holaMundoEJB.saludoEJB(nombre);
    }
}