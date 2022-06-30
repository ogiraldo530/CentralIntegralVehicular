package com.central.integral.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.central.integral.entity.Vehiculo;

public interface IVehiculoDAO extends PagingAndSortingRepository<Vehiculo, Long> {

	@Query(value = "SELECT vehiculo.* FROM vehiculo, cliente WHERE vehiculo.propietario=cliente.id AND (cliente.razon_social LIKE %:busqueda% or vehiculo.placa LIKE %:busqueda%)", nativeQuery = true)
	List<Vehiculo> findByBusqueda(@Param("busqueda") String busqueda);
	
}
