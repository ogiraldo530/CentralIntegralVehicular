package com.central.integral.service;

import com.central.integral.entity.ItemCotizacionServicio;

public interface IItemCotizacionServicioService {
	

	public ItemCotizacionServicio findOne(Long id);
	public void delete(Long id);

}
