package net.bounceme.chronos.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.jbosslog.JBossLog;
import net.bounceme.chronos.dto.RegisterTimeDTO;
import net.bounceme.chronos.service.JmsService;

@Path("/secure/registerTime")
@JBossLog
public class RegisterResource {
	
	@Inject
    private JmsService jmsService;

	@POST
	@RolesAllowed({ "user", "admin" })
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerTime(@Context SecurityContext securityContext, RegisterTimeDTO registerTimeDTO) {
		String username = securityContext.getUserPrincipal().getName();

		log.infof("✅ Usuario autenticado: %s", username);

		jmsService.enviarRegistroTiempo(registerTimeDTO);

		return Response.accepted().entity(registerTimeDTO).build();
	}
}
