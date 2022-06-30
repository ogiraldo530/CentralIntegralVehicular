package com.central.integral.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.central.integral.entity.Herramienta;

public interface IHerramientaService {
	public List<Herramienta> findAll();
	public Page<Herramienta> findAll(Pageable pageable);
	public void save(Herramienta herramienta);
	public Herramienta findOne(Long id);
	public void delete(Long id);
	public List<Herramienta> findByBusqueda(String busqueda) throws Exception;
}
