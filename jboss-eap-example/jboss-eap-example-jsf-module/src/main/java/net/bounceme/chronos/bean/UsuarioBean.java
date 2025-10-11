package net.bounceme.chronos.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import net.bounceme.chronos.ejb.HolaMundoEJB;
import net.bounceme.chronos.utils.Asserts;

@Named
@RequestScoped
public class UsuarioBean {
	
	@Getter
	@Setter
    private String nombre;
    
    @Getter
    private String mensaje;
    
    @Inject
    @Getter
    private HolaMundoEJB holaMundoEJB;
    
    public void saludar() {
    	Asserts.assertNotNull(holaMundoEJB, "Error interno");
    	
    	if (nombre != null && !nombre.trim().isEmpty()) {
            mensaje = holaMundoEJB.saludoPersonalizadoEJB(nombre);
        } else {
            mensaje = holaMundoEJB.saludoEJB();
        }
    }
}