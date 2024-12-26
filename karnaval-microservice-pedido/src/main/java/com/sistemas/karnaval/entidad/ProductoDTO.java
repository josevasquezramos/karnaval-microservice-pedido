package com.sistemas.karnaval.entidad;

import java.util.List;

import lombok.Data;

@Data
public class ProductoDTO {

	private Long id;
    private String nombre;
    private String descripcion;
    private Integer stock;
    private Boolean estado;
    private List<PrecioDTO> precios;
}
