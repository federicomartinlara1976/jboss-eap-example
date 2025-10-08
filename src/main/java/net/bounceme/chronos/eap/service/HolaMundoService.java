package net.bounceme.chronos.eap.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HolaMundoService {
    
    public String getSaludo() {
        return "¡Hola desde JBoss EAP 8!";
    }
    
    public String getSaludoPersonalizado(String nombre) {
        return "¡Hola " + nombre + "! Bienvenido a JBoss EAP 8";
    }
}
