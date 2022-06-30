package com.central.integral.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import com.central.integral.entity.OrdenDeTrabajo;

public interface IOrdenTrabajoDAO extends PagingAndSortingRepository<OrdenDeTrabajo, Long> {
	
	@Query(value = "SELECT orden_de_trabajo.* FROM vehiculo, cliente, cotizacion,  orden_de_trabajo \r\n"
			+ "WHERE cliente.id = vehiculo.propietario AND\r\n"
			+ "vehiculo.id = cotizacion.vehiculo_id AND\r\n"
			+ "orden_de_trabajo.cotizacion_id = cotizacion.id AND\r\n"
			+ "(cliente.razon_social LIKE %:busqueda% OR\r\n"
			+ "vehiculo.placa LIKE %:busqueda% OR \r\n"
			+ "cotizacion.id LIKE %:busqueda% )", nativeQuery = true)
	List<OrdenDeTrabajo> findByBusqueda(@Param("busqueda") String busqueda);
	

}
