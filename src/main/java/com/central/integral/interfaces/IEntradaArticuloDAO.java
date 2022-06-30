package com.central.integral.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.central.integral.entity.EntradaArticulo;

public interface IEntradaArticuloDAO extends PagingAndSortingRepository< EntradaArticulo,Long>{

	@Query(value = "SELECT entrada_articulo.* FROM entrada_articulo, articulo WHERE entrada_articulo.articulo = articulo.id AND (articulo.descripcion LIKE %:busqueda% or entrada_articulo.articulo LIKE %:busqueda%)", nativeQuery = true)
	List<EntradaArticulo> findByBusqueda(@Param("busqueda") String busqueda);
}
