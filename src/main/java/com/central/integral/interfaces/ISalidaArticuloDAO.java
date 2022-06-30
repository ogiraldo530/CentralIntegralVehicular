package com.central.integral.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import com.central.integral.entity.SalidaArticulo;

public interface ISalidaArticuloDAO extends PagingAndSortingRepository< SalidaArticulo,Long>{
	
	@Query(value = "SELECT salida_articulo.* FROM salida_articulo, articulo WHERE salida_articulo.articulo = articulo.id AND (articulo.descripcion LIKE %:busqueda% or salida_articulo.articulo LIKE %:busqueda%)", nativeQuery = true)
	List<SalidaArticulo> findByBusqueda(@Param("busqueda") String busqueda);

}
