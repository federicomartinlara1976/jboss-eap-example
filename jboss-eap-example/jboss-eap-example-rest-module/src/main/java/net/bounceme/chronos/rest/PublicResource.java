package net.bounceme.chronos.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.bounceme.chronos.dto.PublicInfoDTO;
import net.bounceme.chronos.dto.StatusDTO;

@Path("/public")
public class PublicResource {

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublicInfo() {
    	PublicInfoDTO info = PublicInfoDTO.builder()
    			.mensaje("Este endpoint es público")
    			.version("1.0.0")
    			.seguridad("Keycloak OAuth2")
    			.build();
    	
        return Response.ok()
                .entity(info)
                .build();
    }

    @GET
    @Path("/health")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHealth() {
    	StatusDTO status = StatusDTO.builder()
    			.status("OK")
    			.servicio("REST API")
    			.build();
    	
        return Response.ok()
                .entity(status)
                .build();
    }
}
