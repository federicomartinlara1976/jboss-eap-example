package net.bounceme.chronos.config;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import lombok.extern.jbosslog.JBossLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

@Named
@ApplicationScoped
@JBossLog
public class AppConfig implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Properties properties;
    
    @PostConstruct
    public void init() {
        loadProperties();
        logConfiguration();
    }
    
    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("app-config.properties")) {
            if (input != null) {
                properties.load(input);
                log.info("✅ Configuración cargada desde app-config.properties");
            } else {
                log.warn("❌ No se encontró app-config.properties, usando valores por defecto");
                setDefaultProperties();
            }
        } catch (IOException e) {
            log.error("❌ Error cargando configuración, usando valores por defecto", e);
            setDefaultProperties();
        }
    }
    
    private void setDefaultProperties() {
        // Valores por defecto para desarrollo
        properties.setProperty("app.urls.base", "http://localhost:8080");
        properties.setProperty("app.urls.jsf.base", "http://localhost:8080/mi-jsf");
        properties.setProperty("app.urls.jsf.index", "http://localhost:8080/mi-jsf/index.xhtml");
        properties.setProperty("app.urls.jsf.dashboard", "http://localhost:8080/mi-jsf/secure/dashboard.xhtml");
        properties.setProperty("app.urls.jsf.logout-success", "http://localhost:8080/mi-jsf/logout-success.xhtml");
        properties.setProperty("keycloak.base-url", "http://localhost:8082");
        properties.setProperty("keycloak.realm", "jboss-eap-example");
        properties.setProperty("keycloak.client-id", "jboss-eap-app");
        properties.setProperty("keycloak.endpoints.auth", "http://localhost:8082/realms/jboss-eap-example/protocol/openid-connect/auth");
        properties.setProperty("keycloak.endpoints.logout", "http://localhost:8082/realms/jboss-eap-example/protocol/openid-connect/logout");
    }
    
    private void logConfiguration() {
        log.infof("""
            🚀 Configuración de aplicación:
               App Base: %s
               JSF Base: %s
               Keycloak: %s
               Realm: %s
               Client: %s
            """, 
            getBaseUrl(), getJsfBaseUrl(), 
            getKeycloakBaseUrl(), getKeycloakRealm(), getKeycloakClientId());
    }
    
    // 🔥 Producers para inyección directa
    @Produces
    @Named("appBaseUrl")
    public String getBaseUrl() {
        return properties.getProperty("app.urls.base");
    }
    
    @Produces
    @Named("appJsfBaseUrl")
    public String getJsfBaseUrl() {
        return properties.getProperty("app.urls.jsf.base");
    }
    
    @Produces
    @Named("appJsfIndexUrl")
    public String getJsfIndexUrl() {
        return properties.getProperty("app.urls.jsf.index");
    }
    
    @Produces
    @Named("appJsfDashboardUrl")
    public String getJsfDashboardUrl() {
        return properties.getProperty("app.urls.jsf.dashboard");
    }
    
    @Produces
    @Named("appJsfLogoutSuccessUrl")
    public String getJsfLogoutSuccessUrl() {
        return properties.getProperty("app.urls.jsf.logout-success");
    }
    
    @Produces
    @Named("keycloakBaseUrl")
    public String getKeycloakBaseUrl() {
        return properties.getProperty("keycloak.base-url");
    }
    
    @Produces
    @Named("keycloakRealm")
    public String getKeycloakRealm() {
        return properties.getProperty("keycloak.realm");
    }
    
    @Produces
    @Named("keycloakClientId")
    public String getKeycloakClientId() {
        return properties.getProperty("keycloak.client-id");
    }
    
    @Produces
    @Named("keycloakAuthEndpoint")
    public String getKeycloakAuthEndpoint() {
        return properties.getProperty("keycloak.endpoints.auth");
    }
    
    @Produces
    @Named("keycloakLogoutEndpoint")
    public String getKeycloakLogoutEndpoint() {
        return properties.getProperty("keycloak.endpoints.logout");
    }
}