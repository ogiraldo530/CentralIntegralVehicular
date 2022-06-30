package com.central.integral.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.central.integral.entity.OrdenDeTrabajo;

public interface IOrdenTrabajoService {
	public List<OrdenDeTrabajo> findAll();
	public Page<OrdenDeTrabajo> findAll(Pageable pageable);
	public void save(OrdenDeTrabajo ordenDeTrabajo);
	public OrdenDeTrabajo findOne(Long id);
	public void delete(Long id);
	public List<OrdenDeTrabajo> findByBusqueda(String busqueda) throws Exception;

}
