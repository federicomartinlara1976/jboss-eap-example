package net.bounceme.chronos.ws;

import net.bounceme.chronos.ejb.HolaMundoEJB;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public class HolaMundoWS {
    
    @Inject
    private HolaMundoEJB holaMundoEJB;
    
    @WebMethod
    public String saludar() {
        return holaMundoEJB.saludoEJB();
    }
    
    @WebMethod
    public String saludarPersonalizado(String nombre) {
        return holaMundoEJB.saludoPersonalizadoEJB(nombre);
    }
}