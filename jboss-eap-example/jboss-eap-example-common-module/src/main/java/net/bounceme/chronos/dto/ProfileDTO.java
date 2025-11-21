package net.bounceme.chronos.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfileDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String usuario;
	
	private Boolean isAdmin;
	
	private Boolean isUser;
	
	private Boolean autenticado;
	
	private String authScheme;
	
	private Boolean isSecure;
}
