package net.bounceme.chronos.dto;

import java.io.Serializable;
import java.util.Date;

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
public class RegisterTimeDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String usuario;
	
	private Date hora;
	
	private String tipo;
}
