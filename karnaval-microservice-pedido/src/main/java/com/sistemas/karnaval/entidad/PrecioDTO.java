package com.sistemas.karnaval.entidad;

import lombok.Data;

@Data
public class PrecioDTO {

	private Long id;
	private String stripePriceId;
	private String currency;
	private Double unitAmount;
	private Boolean active;
}
