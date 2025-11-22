package net.bounceme.chronos.utils;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.bounceme.chronos.exceptions.AssertException;

@UtilityClass
public class JsfUtils {

	@SneakyThrows(AssertException.class)
	public void writeMessage(FacesMessage.Severity severity, String title, String detail) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Asserts.assertNotNull(facesContext);
		
		facesContext.addMessage(null, new FacesMessage(severity, title, detail));
	}
	
	@SneakyThrows(AssertException.class)
	public ExternalContext getExternalContext() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Asserts.assertNotNull(facesContext);

		return facesContext.getExternalContext();
	}
}
