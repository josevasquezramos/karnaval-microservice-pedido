package com.sistemas.karnaval.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemas.karnaval.entidad.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	List<Pedido> findByIdCliente(Long idCliente);
}
