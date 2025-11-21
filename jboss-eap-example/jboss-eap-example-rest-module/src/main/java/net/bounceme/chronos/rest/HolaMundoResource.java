package net.bounceme.chronos.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.bounceme.chronos.ejb.HolaMundoEJB;

@Path("/public/hola")
public class HolaMundoResource {
    
    @Inject
    private HolaMundoEJB holaMundoEJB;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSaludo() {
        return Response.ok(holaMundoEJB.saludoEJB()).build();
    }
    
    @GET
    @Path("/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSaludoPersonalizado(@PathParam("nombre") String nombre) {
    	return Response.ok(holaMundoEJB.saludoEJB(nombre)).build();
    }
}