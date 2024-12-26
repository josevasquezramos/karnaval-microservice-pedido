package com.sistemas.karnaval.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sistemas.karnaval.entidad.ProductoDTO;

@FeignClient(name = "karnaval-microservice-inventario", path = "/api/productos")
public interface ProductoFeignClient {

	@GetMapping("/{id}")
	ProductoDTO obtenerProductoPorId(@PathVariable("id") Long id);
}
