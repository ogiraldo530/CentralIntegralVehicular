package com.central.integral.Implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.central.integral.entity.ItemCotizacionServicio;
import com.central.integral.interfaces.IItemServicioDAO;

import com.central.integral.service.IItemCotizacionServicioService;

@Service
public class IItemCotizacionServicioServiceImplement implements IItemCotizacionServicioService {
	

	@Autowired
	private IItemServicioDAO itemServicioDAO;
	
	@Override
	@Transactional(readOnly = true)
	public ItemCotizacionServicio findOne(Long id) {
		return itemServicioDAO.findById(id).orElse(null);
	}



	@Override
	@Transactional
	public void delete(Long id) {
		itemServicioDAO.deleteById(id);

	}

}
