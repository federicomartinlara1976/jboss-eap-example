package net.bounceme.chronos.utils;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsfUtils {

	public void writeMessage(FacesMessage.Severity severity, String title, String detail) {
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage(severity, title, detail));
	}
}
