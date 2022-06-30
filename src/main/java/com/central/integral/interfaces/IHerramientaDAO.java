package com.central.integral.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.central.integral.entity.Herramienta;



public interface IHerramientaDAO extends PagingAndSortingRepository<Herramienta, Long> {
	@Query(value = "SELECT * FROM herramienta WHERE herramienta.descripcion LIKE %:busqueda% or herramienta.marca LIKE %:busqueda%", nativeQuery = true)
	List<Herramienta> findByBusqueda(@Param("busqueda") String busqueda);
	
}
