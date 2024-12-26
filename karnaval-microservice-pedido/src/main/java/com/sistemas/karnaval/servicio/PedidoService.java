package com.sistemas.karnaval.servicio;

import java.util.List;

import com.sistemas.karnaval.entidad.Pedido;

public interface PedidoService extends iGenericoService<Pedido, Long> {

	List<Pedido> listarPorCliente(Long idCliente);

	Pedido actualizarEstado(Long id, String estado);
}
