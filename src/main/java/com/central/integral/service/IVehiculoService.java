package com.central.integral.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.central.integral.entity.Vehiculo;

public interface IVehiculoService {
	
	public List<Vehiculo> findAll();
	public Page<Vehiculo> findAll(Pageable pageable);
	public void save(Vehiculo vehiculo);
	public Vehiculo findOne(Long id);
	public void delete(Long id);
	public List<Vehiculo> findByBusqueda(String busqueda) throws Exception;

}
