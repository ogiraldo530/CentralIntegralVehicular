package com.central.integral.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.central.integral.entity.Articulo;


public interface IArticuloDAO extends PagingAndSortingRepository<Articulo, Long> {
	@Query(value = "SELECT * FROM articulo WHERE articulo.descripcion LIKE %:busqueda% or articulo.categoria LIKE %:busqueda%", nativeQuery = true)
	List<Articulo> findByBusqueda(@Param("busqueda") String busqueda);
	
	@Query(value = "SELECT * FROM articulo WHERE articulo.descripcion like %?1%", nativeQuery = true)
	public List<Articulo> findByDescripcion(String term);
	

}
