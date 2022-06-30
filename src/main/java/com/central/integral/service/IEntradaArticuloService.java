package com.central.integral.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.central.integral.entity.EntradaArticulo;


public interface IEntradaArticuloService {
	
	public List<EntradaArticulo> findAll();
	public Page<EntradaArticulo> findAll(Pageable pageable);
	public void save(EntradaArticulo entradaArticulo);
	public EntradaArticulo findOne(Long id);
	public void delete(Long id);
	public List<EntradaArticulo> findByBusqueda(String busqueda) throws Exception;

}
