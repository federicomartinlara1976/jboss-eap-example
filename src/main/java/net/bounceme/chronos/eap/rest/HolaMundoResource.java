package net.bounceme.chronos.eap.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.bounceme.chronos.eap.service.HolaMundoService;

@Path("/hola")
public class HolaMundoResource {
    
    @Inject
    private HolaMundoService servicio;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSaludo() {
        return Response.ok().entity("{\"mensaje\": \"" + servicio.getSaludo() + "\"}").build();
    }
    
    @GET
    @Path("/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSaludoPersonalizado(@PathParam("nombre") String nombre) {
        String mensaje = servicio.getSaludoPersonalizado(nombre);
        return Response.ok().entity("{\"mensaje\": \"" + mensaje + "\"}").build();
    }
}
