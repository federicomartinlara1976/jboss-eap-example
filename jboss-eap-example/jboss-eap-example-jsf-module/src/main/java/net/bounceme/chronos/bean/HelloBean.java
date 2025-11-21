package net.bounceme.chronos.bean;

import org.apache.commons.lang3.StringUtils;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import net.bounceme.chronos.service.HolaMundoEJB;
import net.bounceme.chronos.utils.Asserts;

@Named
@RequestScoped
public class HelloBean {

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

		mensaje = (StringUtils.isNotBlank(nombre)) ? 
				holaMundoEJB.saludoEJB(nombre).getMensaje() : 
				holaMundoEJB.saludoEJB().getMensaje();
	}
}