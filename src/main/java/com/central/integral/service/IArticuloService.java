package com.central.integral.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.central.integral.entity.Articulo;


public interface IArticuloService {
	public List<Articulo> findAll();
	public Page<Articulo> findAll(Pageable pageable);
	public void save(Articulo articulo);
	public Articulo findOne(Long id);
	public void delete(Long id);
	public List<Articulo> findByBusqueda(String busqueda) throws Exception;
	
	public List<Articulo> findByDescripcion(String term);
	

}
