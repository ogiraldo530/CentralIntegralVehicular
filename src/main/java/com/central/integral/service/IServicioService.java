package com.central.integral.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.central.integral.entity.Servicio;
public interface IServicioService {

	public List<Servicio> findAll();
	public Page<Servicio> findAll(Pageable pageable);
	public void save(Servicio servicio);
	public Servicio findOne(Long id);
	public void delete(Long id);
	public List<Servicio> findByBusqueda(String busqueda) throws Exception;
	public List<Servicio> findByDescripcion(String term);
}
