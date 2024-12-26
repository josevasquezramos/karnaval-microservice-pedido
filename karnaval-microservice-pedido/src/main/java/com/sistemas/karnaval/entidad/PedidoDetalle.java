package com.sistemas.karnaval.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pedido_detalles")
@Data
public class PedidoDetalle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "pedido_id", nullable = false)
	@JsonIgnore
	private Pedido pedido;

	@Column(nullable = false)
	private Long idProducto;

	@Column(nullable = false)
	private Integer cantidad;

	@Column(nullable = false)
	private Double precioUnitario;

	@Column(nullable = false)
	private Double subtotal;
}
