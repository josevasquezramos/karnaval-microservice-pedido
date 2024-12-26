package com.sistemas.karnaval.entidad;

import lombok.Data;

@Data
public class PedidoDetalleDTO {

	private Long id;
	private ProductoDTO producto;
	private Integer cantidad;
	private Double precioUnitario;
	private Double subtotal;
}
