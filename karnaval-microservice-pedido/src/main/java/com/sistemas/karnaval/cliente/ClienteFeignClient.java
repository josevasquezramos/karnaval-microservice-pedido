package com.sistemas.karnaval.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sistemas.karnaval.entidad.ClienteDTO;

@FeignClient(name = "karnaval-microservice-cliente", path = "/api/clientes")
public interface ClienteFeignClient {

	@GetMapping("/{id}")
	ClienteDTO obtenerClientePorId(@PathVariable("id") Long id);
}
