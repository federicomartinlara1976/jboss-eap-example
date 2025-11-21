package net.bounceme.chronos.bean;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.SneakyThrows;
import lombok.extern.jbosslog.JBossLog;
import net.bounceme.chronos.config.AppConfig;
import net.bounceme.chronos.utils.JsfUtils;

@Named
@SessionScoped
@JBossLog
public class SecurityBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String URL_LOGOUT = "%s/realms/%s/protocol/openid-connect/logout?client_id=%s&post_logout_redirect_uri=%s";

	@Inject
	private AppConfig appConfig;

	/**
	 * Logout usando client_id - Funciona sin id_token Keycloak mostrará su página
	 * de logout nativa
	 */
	public void logoutWithClientId() {
		ExternalContext externalContext = JsfUtils.getExternalContext();

		String username = externalContext.getUserPrincipal() != null ? externalContext.getUserPrincipal().getName()
				: "unknown";

		log.infof("🚪 Cerrando sesión para usuario: %s usando client_id", username);

		try {
			// Construir URL de logout con client_id
			String logoutUrl = buildKeycloakLogoutUrlWithClientId();

			log.infof("🔐 Redirigiendo a logout de Keycloak: %s", logoutUrl);

			// Invalidar sesión local primero
			externalContext.invalidateSession();

			// Redirigir a Keycloak
			externalContext.redirect(logoutUrl);

		} catch (Exception e) {
			log.error("Error durante logout con client_id", e);
			// Fallback a logout local
			logoutLocalOnly();
		}
	}

	/**
	 * Construye URL de logout de Keycloak usando client_id Keycloak mostrará su
	 * propia página de confirmación
	 */
	private String buildKeycloakLogoutUrlWithClientId() {
		String redirectUri = URLEncoder.encode(appConfig.getJsfBaseUrl(), Charset.defaultCharset());

		return String.format(URL_LOGOUT, appConfig.getKeycloakBaseUrl(), appConfig.getKeycloakRealm(),
				appConfig.getKeycloakClientId(), redirectUri);
	}

	// Método de logout simple (solo sesión local)
	@SneakyThrows(IOException.class)
	public void logoutLocalOnly() {
		ExternalContext externalContext = JsfUtils.getExternalContext();
		externalContext.invalidateSession();
		externalContext.redirect(appConfig.getJsfBaseUrl());
	}

	public boolean isUserInRole(String role) {
		ExternalContext externalContext = JsfUtils.getExternalContext();
		return externalContext.isUserInRole(role);
	}

	public String getUsername() {
		ExternalContext externalContext = JsfUtils.getExternalContext();
		return externalContext.getUserPrincipal() != null ? externalContext.getUserPrincipal().getName()
				: "No autenticado";
	}

	public boolean isAuthenticated() {
		ExternalContext externalContext = JsfUtils.getExternalContext();
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
}
