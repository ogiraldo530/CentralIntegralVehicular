package com.central.integral.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.central.integral.entity.Cotizacion;
import com.central.integral.entity.ItemCotizacionArticulo;
import com.central.integral.entity.ItemCotizacionServicio;


public interface ICotizacionService {

	public List<Cotizacion> findAll();
	public Page<Cotizacion> findAll(Pageable pageable);
	public void save(Cotizacion cotizacion);
	public Cotizacion findOne(Long id);
	public void delete(Long id);
	public List<Cotizacion> findByBusqueda(String busqueda) throws Exception;
}
