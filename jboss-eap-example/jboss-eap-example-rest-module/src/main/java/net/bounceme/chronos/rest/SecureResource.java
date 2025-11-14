package net.bounceme.chronos.rest;

import java.util.Arrays;
import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.jbosslog.JBossLog;
import net.bounceme.chronos.dto.MensajeDTO;
import net.bounceme.chronos.dto.ProfileDTO;
import net.bounceme.chronos.utils.Constants;

@Path("/secure")
@JBossLog
public class SecureResource {

	@GET
	@Path("/user")
	@RolesAllowed("user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserInfo(@Context SecurityContext securityContext) {
		String username = securityContext.getUserPrincipal().getName();

		log.infof("✅ Usuario autenticado: %s", username);

		String[] arrayRoles = { Constants.ROLES.ROLE_USER.getName() };
		List<String> roles = Arrays.stream(arrayRoles).toList(); // Inmutable - Java 16+

		MensajeDTO mensaje = MensajeDTO.builder().mensaje("Hola usuario autenticado").usuario(username).roles(roles)
				.build();

		return Response.ok().entity(mensaje).build();
	}

	@GET
	@Path("/admin")
	@RolesAllowed("admin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAdminInfo(@Context SecurityContext securityContext) {
		String username = securityContext.getUserPrincipal().getName();

		log.infof("✅ Admin autenticado: %s", username);

		String[] arrayRoles = { Constants.ROLES.ROLE_ADMIN.getName(), 
				Constants.ROLES.ROLE_USER.getName() };
		
		List<String> roles = Arrays.stream(arrayRoles).toList(); // Inmutable - Java 16+

		MensajeDTO mensaje = MensajeDTO.builder().mensaje("Hola administrador").usuario(username).roles(roles).build();

		return Response.ok().entity(mensaje).build();
	}

	@GET
	@Path("/profile")
	@RolesAllowed({ "user", "admin" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserProfile(@Context SecurityContext securityContext) {
		String username = securityContext.getUserPrincipal().getName();
		boolean isAdmin = securityContext.isUserInRole(Constants.ROLES.ROLE_ADMIN.getName());
		boolean isUser = securityContext.isUserInRole(Constants.ROLES.ROLE_USER.getName());

		log.infof("✅ Perfil consultado: %s, Admin: %s, User: %s", username, isAdmin, isUser);

		ProfileDTO profile = ProfileDTO.builder().usuario(username).isAdmin(isAdmin).isUser(isUser)
				.autenticado(Boolean.TRUE).build();

		return Response.ok().entity(profile).build();
	}

	@GET
	@Path("/info")
	@RolesAllowed({ "user", "admin" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSecurityInfo(@Context SecurityContext securityContext) {
		String username = securityContext.getUserPrincipal().getName();
		String authScheme = securityContext.getAuthenticationScheme();
		boolean isAdmin = securityContext.isUserInRole(Constants.ROLES.ROLE_ADMIN.getName());
		boolean isUser = securityContext.isUserInRole(Constants.ROLES.ROLE_USER.getName());
		boolean isSecure = securityContext.isSecure();

		ProfileDTO profile = ProfileDTO.builder().usuario(username).isAdmin(isAdmin).isUser(isUser).isSecure(isSecure)
				.authScheme(authScheme).build();

		return Response.ok().entity(profile).build();
	}
}
