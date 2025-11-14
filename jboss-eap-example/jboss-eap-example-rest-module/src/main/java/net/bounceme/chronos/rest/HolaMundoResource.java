package net.bounceme.chronos.rest;

import net.bounceme.chronos.dto.MensajeDTO;
import net.bounceme.chronos.ejb.HolaMundoEJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/hola")
public class HolaMundoResource {
    
    @Inject
    private HolaMundoEJB holaMundoEJB;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSaludo() {
        MensajeDTO mensaje = MensajeDTO.builder()
        		.mensaje(holaMundoEJB.saludoEJB())
        		.build();
        
        return Response.ok(mensaje).build();
    }
    
    @GET
    @Path("/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSaludoPersonalizado(@PathParam("nombre") String nombre) {
    	MensajeDTO mensaje = MensajeDTO.builder()
        		.mensaje(holaMundoEJB.saludoPersonalizadoEJB(nombre))
        		.build();
    	
        return Response.ok(mensaje).build();
    }
}