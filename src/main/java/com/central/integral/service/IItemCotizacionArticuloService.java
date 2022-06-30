package com.central.integral.service;

import com.central.integral.entity.ItemCotizacionArticulo;

public interface IItemCotizacionArticuloService {
	
	public ItemCotizacionArticulo findOne(Long id);
	public void delete(Long id);
	public void save(ItemCotizacionArticulo itemCotizacionArticulo);

}
