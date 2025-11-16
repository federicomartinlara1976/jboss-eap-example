package net.bounceme.chronos.bean;

import java.io.IOException;
import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.SneakyThrows;
import lombok.extern.jbosslog.JBossLog;
import net.bounceme.chronos.utils.AssertException;
import net.bounceme.chronos.utils.Asserts;

@Named
@RequestScoped
@JBossLog
public class SecurityBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@SneakyThrows(IOException.class)
	public void logout() {
		ExternalContext externalContext = getExternalContext();

		String username = externalContext.getUserPrincipal() != null ? externalContext.getUserPrincipal().getName()
				: "unknown";

		log.infof("🚪 Cerrando sesión para usuario: %s", username);

		// Invalidar sesión
		externalContext.invalidateSession();

		// Redirigir a logout de Keycloak con redirect_uri
		String redirectUri = java.net.URLEncoder.encode("http://192.168.1.135:8080/mi-jsf/index.xhtml", "UTF-8");
		String keycloakLogoutUrl = "http://192.168.1.135:8082/realms/jboss-eap-example/protocol/openid-connect/logout"
				+ "?redirect_uri=" + redirectUri;

		log.infof("🔐 Redirigiendo a Keycloak logout: %s", keycloakLogoutUrl);
		externalContext.redirect(keycloakLogoutUrl);
	}

	public boolean isUserInRole(String role) {
		ExternalContext externalContext = getExternalContext();
		return externalContext.isUserInRole(role);
	}

	public String getUsername() {
		ExternalContext externalContext = getExternalContext();
		return externalContext.getUserPrincipal() != null ? externalContext.getUserPrincipal().getName()
				: "No autenticado";
	}

	public boolean isAuthenticated() {
		ExternalContext externalContext = getExternalContext();
		return externalContext.getUserPrincipal() != null;
	}

	public String getUserRoles() {
		StringBuilder roles = new StringBuilder();
		if (isUserInRole("user"))
			roles.append("user ");
		if (isUserInRole("admin"))
			roles.append("admin ");

		return roles.toString().trim();
	}

	public String getAuthInfo() {
		if (!isAuthenticated()) {
			return "No autenticado";
		}

		return String.format("Usuario: %s, Roles: [%s]", getUsername(), getUserRoles());
	}

	@SneakyThrows(AssertException.class)
	private ExternalContext getExternalContext() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Asserts.assertNotNull(facesContext);

		return facesContext.getExternalContext();
	}
}
