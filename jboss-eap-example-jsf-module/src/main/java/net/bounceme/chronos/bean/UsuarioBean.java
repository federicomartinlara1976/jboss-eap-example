package net.bounceme.chronos.bean;

import net.bounceme.chronos.ejb.HolaMundoEJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class UsuarioBean {
    
    private String nombre;
    private String mensaje;
    
    @Inject
    private HolaMundoEJB holaMundoEJB;
    
    public void saludar() {
        if (nombre != null && !nombre.trim().isEmpty()) {
            mensaje = holaMundoEJB.saludoPersonalizadoEJB(nombre);
        } else {
            mensaje = holaMundoEJB.saludoEJB();
        }
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMensaje() { return mensaje; }
}