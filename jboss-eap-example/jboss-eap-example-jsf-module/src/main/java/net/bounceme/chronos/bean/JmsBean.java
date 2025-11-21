package net.bounceme.chronos.bean;

import org.apache.commons.lang3.StringUtils;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import net.bounceme.chronos.exceptions.ServiceException;
import net.bounceme.chronos.jms.JmsService;
import net.bounceme.chronos.utils.Asserts;
import net.bounceme.chronos.utils.JsfUtils;

@Named
@RequestScoped
public class JmsBean {

	@Getter
	@Setter
	private String mensaje;

	@Inject
	@Getter
	private JmsService jmsService;

	public void enviar() {
		try {
			Asserts.assertNotNull(jmsService, "Error interno");
			Asserts.assertTrue(StringUtils.isNotBlank(mensaje), "Debe rellenar el mensaje");

			jmsService.enviarMensaje(mensaje);
			JsfUtils.writeMessage(FacesMessage.SEVERITY_INFO, "Envío correcto", "El envío se ha realizado correctamente");
		} catch (ServiceException e) {
			JsfUtils.writeMessage(FacesMessage.SEVERITY_ERROR, "Error envío", e.getMessage());	
		}
	}
	
	
}