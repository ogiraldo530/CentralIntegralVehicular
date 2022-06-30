package com.central.integral.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.central.integral.entity.SalidaArticulo;

public interface ISalidaArticuloService {
	
	public List<SalidaArticulo> findAll();
	public Page<SalidaArticulo> findAll(Pageable pageable);
	public void save(SalidaArticulo salidaArticulo);
	public SalidaArticulo findOne(Long id);
	public void delete(Long id);
	public List<SalidaArticulo> findByBusqueda(String busqueda) throws Exception;

}
