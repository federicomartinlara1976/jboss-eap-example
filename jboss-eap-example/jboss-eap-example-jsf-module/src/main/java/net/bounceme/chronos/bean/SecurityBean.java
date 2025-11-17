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

	public void logout() throws IOException {
		ExternalContext externalContext = getExternalContext();

		String username = externalContext.getUserPrincipal() != null ? externalContext.getUserPrincipal().getName()
				: "unknown";

		log.infof("🚪 Cerrando sesión para usuario: %s", username);

		// Invalidar sesión local primero
		externalContext.invalidateSession();

		try {
			// Construir URL de logout de Keycloak con los nuevos parámetros
			String redirectUri = java.net.URLEncoder.encode("http://192.168.1.135:8080/mi-jsf/index.xhtml", "UTF-8");

			// 🔥 CAMBIO IMPORTANTE: Usar post_logout_redirect_uri en lugar de redirect_uri
			String keycloakLogoutUrl = "http://192.168.1.135:8082/realms/jboss-eap-example/protocol/openid-connect/logout"
					+ "?post_logout_redirect_uri=" + redirectUri;

			log.infof("🔐 Redirigiendo a Keycloak logout: %s", keycloakLogoutUrl);
			externalContext.redirect(keycloakLogoutUrl);

		} catch (Exception e) {
			log.error("Error durante logout", e);
			// Fallback: redirigir a la página principal
			externalContext.redirect("http://192.168.1.135:8080/mi-jsf/index.xhtml");
		}
	}

	// Versión mejorada con id_token_hint (recomendada)
	public void logoutWithToken() throws IOException {
		ExternalContext externalContext = getExternalContext();

		String username = externalContext.getUserPrincipal() != null ? externalContext.getUserPrincipal().getName()
				: "unknown";

		log.infof("🚪 Cerrando sesión (con token) para usuario: %s", username);

		try {
			// Construir URL de logout más robusta
			String redirectUri = java.net.URLEncoder.encode("http://192.168.1.135:8080/mi-jsf/index.xhtml", "UTF-8");

			// 🔥 URL de logout con ambos parámetros recomendados
			String keycloakLogoutUrl = "http://192.168.1.135:8082/realms/jboss-eap-example/protocol/openid-connect/logout"
					+ "?post_logout_redirect_uri=" + redirectUri;

			// Nota: En una aplicación real, aquí se incluiría también el id_token_hint
			// pero requiere obtener el token de la sesión, lo cual es más complejo

			log.infof("🔐 Redirigiendo a Keycloak logout mejorado: %s", keycloakLogoutUrl);

			// Invalidar sesión local después de construir la URL
			externalContext.invalidateSession();
			externalContext.redirect(keycloakLogoutUrl);

		} catch (Exception e) {
			log.error("Error durante logout con token", e);
			// Fallback seguro
			externalContext.invalidateSession();
			externalContext.redirect("http://192.168.1.135:8080/mi-jsf/index.xhtml");
		}
	}

	// Método de logout simple (solo sesión local)
	public void logoutLocalOnly() throws IOException {
		ExternalContext externalContext = getExternalContext();
		externalContext.invalidateSession();
		externalContext.redirect("http://192.168.1.135:8080/mi-jsf/index.xhtml");
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
