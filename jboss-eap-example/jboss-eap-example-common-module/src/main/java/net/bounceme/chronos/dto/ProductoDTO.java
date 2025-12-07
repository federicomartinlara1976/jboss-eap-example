package net.bounceme.chronos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class ProductoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String nombre;
	
	private String categoria;
	
	private BigDecimal precio;
}
