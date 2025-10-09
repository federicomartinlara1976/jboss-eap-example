package net.bounceme.chronos.rest;

import com.ejemplo.ejb.HolaMundoEJB;
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
        String mensaje = holaMundoEJB.saludoEJB();
        return Response.ok("{\"mensaje\": \"" + mensaje + "\"}").build();
    }
    
    @GET
    @Path("/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSaludoPersonalizado(@PathParam("nombre") String nombre) {
        String mensaje = holaMundoEJB.saludoPersonalizadoEJB(nombre);
        return Response.ok("{\"mensaje\": \"" + mensaje + "\"}").build();
    }
}