package com.sistemas.karnaval.controlador;

import com.sistemas.karnaval.entidad.Pedido;
import com.sistemas.karnaval.servicio.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@PostMapping
	public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido) {
		return ResponseEntity.ok(pedidoService.crear(pedido));
	}

	@GetMapping("/cliente/{idCliente}")
	public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable Long idCliente) {
		return ResponseEntity.ok(pedidoService.listarPorCliente(idCliente));
	}

	@GetMapping
	public ResponseEntity<List<Pedido>> listarTodos() {
		return ResponseEntity.ok(pedidoService.listarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
		return ResponseEntity.ok(pedidoService.buscarPorId(id));
	}

	@PutMapping("/{id}/estado")
	public ResponseEntity<Pedido> actualizarEstado(@PathVariable Long id, @RequestBody String estado) {
		return ResponseEntity.ok(pedidoService.actualizarEstado(id, estado));
	}
}
