package net.bounceme.chronos.bean;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.SneakyThrows;
import lombok.extern.jbosslog.JBossLog;
import net.bounceme.chronos.config.AppConfig;
import net.bounceme.chronos.utils.AssertException;
import net.bounceme.chronos.utils.Asserts;

@Named
@RequestScoped
@JBossLog
public class SecurityBean implements Serializable {

	private static final long serialVersionUID = 1L;
    
    @Inject
    private AppConfig appConfig;

    /**
     * Logout usando client_id - Funciona sin id_token
     * Keycloak mostrará su página de logout nativa
     */
    public void logoutWithClientId() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            ExternalContext externalContext = facesContext.getExternalContext();
            
            String username = externalContext.getUserPrincipal() != null 
                ? externalContext.getUserPrincipal().getName() 
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
    }

    /**
     * Construye URL de logout de Keycloak usando client_id
     * Keycloak mostrará su propia página de confirmación
     */
    private String buildKeycloakLogoutUrlWithClientId() {
        String redirectUri = URLEncoder.encode(
            appConfig.getJsfBaseUrl());
        
        return appConfig.getKeycloakBaseUrl() + "/realms/" + appConfig.getKeycloakRealm() + "/protocol/openid-connect/logout" +
               "?client_id=" + appConfig.getKeycloakClientId() +
               "&post_logout_redirect_uri=" + redirectUri;
    }

	// Método de logout simple (solo sesión local)
	public void logoutLocalOnly() throws IOException {
		ExternalContext externalContext = getExternalContext();
		externalContext.invalidateSession();
		externalContext.redirect(appConfig.getJsfBaseUrl());
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
