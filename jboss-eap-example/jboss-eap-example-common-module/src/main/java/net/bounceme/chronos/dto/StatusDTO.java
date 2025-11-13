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
public class StatusDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String status;
	
	private String servicio;
}
