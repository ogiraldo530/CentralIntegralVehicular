package com.central.integral.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import com.central.integral.entity.Servicio;

public interface IServicioDAO extends PagingAndSortingRepository<Servicio, Long> {
	@Query(value = "SELECT * FROM servicio WHERE servicio.descripcion LIKE %:busqueda% or servicio.id LIKE %:busqueda% ", nativeQuery = true)
	List<Servicio> findByBusqueda(@Param("busqueda") String busqueda);
	
	@Query(value = "SELECT * FROM servicio WHERE servicio.descripcion like %?1%", nativeQuery = true)
	public List<Servicio> findByDescripcion(String term);

}
