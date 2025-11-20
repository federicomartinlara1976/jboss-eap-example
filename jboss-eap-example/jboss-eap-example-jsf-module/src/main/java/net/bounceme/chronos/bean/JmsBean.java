package net.bounceme.chronos.bean;

import org.apache.commons.lang3.StringUtils;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import net.bounceme.chronos.jms.JmsService;
import net.bounceme.chronos.utils.Asserts;

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
			writeMessage(FacesMessage.SEVERITY_INFO, "Envío correcto", "El envío se ha realizado correctamente");
		} catch (Exception e) {
			writeMessage(FacesMessage.SEVERITY_ERROR, "Error envío", e.getMessage());	
		}
	}
	
	private void writeMessage(FacesMessage.Severity severity, String title, String detail) {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage(severity, title, detail));
	}
}