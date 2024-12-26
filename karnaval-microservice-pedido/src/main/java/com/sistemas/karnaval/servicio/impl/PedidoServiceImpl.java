package com.sistemas.karnaval.servicio.impl;

import com.sistemas.karnaval.cliente.ClienteFeignClient;
import com.sistemas.karnaval.cliente.ProductoFeignClient;
import com.sistemas.karnaval.entidad.ClienteDTO;
import com.sistemas.karnaval.entidad.Pedido;
import com.sistemas.karnaval.entidad.PedidoDetalle;
import com.sistemas.karnaval.entidad.PrecioDTO;
import com.sistemas.karnaval.entidad.ProductoDTO;
import com.sistemas.karnaval.repositorio.PedidoDetalleRepository;
import com.sistemas.karnaval.repositorio.PedidoRepository;
import com.sistemas.karnaval.servicio.PedidoService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PedidoDetalleRepository pedidoDetalleRepository;

	@Autowired
	private ProductoFeignClient productoFeignClient;

	@Autowired
	private ClienteFeignClient clienteFeignClient;

	@Override
	public Pedido crear(Pedido pedido) {
		try {
			// Crear el PaymentIntent en Stripe
			PaymentIntentCreateParams.Builder paramsBuilder = PaymentIntentCreateParams.builder()
					.setCurrency("pen")
					.setDescription("Pedido en Karnaval");

			double total = 0.0;

			// Procesar los detalles del pedido
			for (PedidoDetalle detalle : pedido.getDetalles()) {
				ProductoDTO producto = productoFeignClient.obtenerProductoPorId(detalle.getIdProducto());

				if (producto == null || !producto.getEstado()) {
					throw new RuntimeException("Producto no encontrado o inactivo");
				}

				// Obtener el precio activo del producto
				Optional<PrecioDTO> precioActivo = producto.getPrecios().stream()
						.filter(PrecioDTO::getActive)
						.findFirst();

				if (precioActivo.isEmpty()) {
					throw new RuntimeException("No hay precios activos para el producto con ID: " + detalle.getIdProducto());
				}

				// Calcular precio unitario y subtotal
				Double precioUnitario = precioActivo.get().getUnitAmount().doubleValue();
				detalle.setPrecioUnitario(precioUnitario);
				detalle.setSubtotal(precioUnitario * detalle.getCantidad());
				total += detalle.getSubtotal();

				// Agregar a Stripe la información para el pago
				paramsBuilder.addPaymentMethodType("card");
			}

			// Crear el PaymentIntent en Stripe
			PaymentIntent paymentIntent = PaymentIntent.create(paramsBuilder.setAmount((long) (total * 100)).build());

			// Configurar el pedido
			pedido.setFecha(LocalDate.now());
			pedido.setTotal(total);
			pedido.setStripePaymentId(paymentIntent.getId());
			pedido.setEstado("Pendiente");

			// Guardar el pedido y sus detalles en la base de datos
			for (PedidoDetalle detalle : pedido.getDetalles()) {
				detalle.setPedido(pedido);
			}

			return pedidoRepository.save(pedido);

		} catch (StripeException e) {
			throw new RuntimeException("Error al procesar el pago en Stripe: " + e.getMessage());
		}
	}

	@Override
	public List<Pedido> listarPorCliente(Long idCliente) {
		return pedidoRepository.findByIdCliente(idCliente);
	}

	@Override
	public List<Pedido> listarTodos() {
		return pedidoRepository.findAll();
	}

	@Override
	public Pedido buscarPorId(Long id) {
		return pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
	}

	@Override
	public Pedido actualizarEstado(Long id, String estado) {
		Pedido pedido = buscarPorId(id);
		pedido.setEstado(estado);
		return pedidoRepository.save(pedido);
	}

	@Override
	public Pedido actualizar(Long id, Pedido entidad) {
		throw new UnsupportedOperationException("No implementado para el servicio de pedidos");
	}

	@Override
	public void eliminar(Long id) {
		pedidoRepository.deleteById(id);
	}
}
