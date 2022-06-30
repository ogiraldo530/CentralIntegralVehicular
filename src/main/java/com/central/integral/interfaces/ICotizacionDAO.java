package com.central.integral.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.central.integral.entity.Articulo;
import com.central.integral.entity.Cotizacion;
import com.central.integral.entity.ItemCotizacionArticulo;



public interface ICotizacionDAO extends PagingAndSortingRepository<Cotizacion, Long>{
	@Query(value = "SELECT * FROM cotizacion WHERE cotizacion.fecha LIKE %:busqueda%", nativeQuery = true)
	List<Cotizacion> findByBusqueda(@Param("busqueda") String busqueda);
	
}
