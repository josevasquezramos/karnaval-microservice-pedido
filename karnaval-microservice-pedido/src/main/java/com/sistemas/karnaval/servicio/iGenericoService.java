package com.sistemas.karnaval.servicio;

import java.util.List;

public interface iGenericoService<T, ID> {

	T crear(T entidad);

	List<T> listarTodos();

	T buscarPorId(ID id);

	T actualizar(ID id, T entidad);

	void eliminar(ID id);
}
