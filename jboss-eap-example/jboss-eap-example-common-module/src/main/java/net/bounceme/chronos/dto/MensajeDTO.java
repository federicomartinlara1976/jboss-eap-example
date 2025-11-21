package net.bounceme.chronos.dto;

import java.io.Serializable;
import java.util.List;

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
public class MensajeDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String mensaje;
	
	private String usuario;
	
	private List<String> roles;
}
