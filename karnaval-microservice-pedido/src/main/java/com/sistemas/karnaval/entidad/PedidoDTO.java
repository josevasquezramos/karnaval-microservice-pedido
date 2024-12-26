package com.sistemas.karnaval.entidad;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class PedidoDTO {

	private Long id;
	private ClienteDTO cliente;
	private LocalDate fecha;
	private Double total;
	private String stripePaymentId;
	private String estado;
	private List<PedidoDetalleDTO> detalles;
}
