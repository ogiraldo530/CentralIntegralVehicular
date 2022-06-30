package com.central.integral.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.central.integral.entity.Cliente;

public interface IClientesDAO extends PagingAndSortingRepository<Cliente, Long> {
	@Query(value = "SELECT * FROM cliente WHERE cliente.razon_social LIKE %:busqueda% or cliente.nit LIKE %:busqueda% or cliente.telefono LIKE %:busqueda%", nativeQuery = true)
	List<Cliente> findByRazonSocial(@Param("busqueda") String busqueda);

}
