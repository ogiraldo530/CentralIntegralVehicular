package com.central.integral.Implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.central.integral.entity.ItemCotizacionArticulo;
import com.central.integral.interfaces.IItemArticuloDAO;
import com.central.integral.service.IItemCotizacionArticuloService;

@Service
public class IItemCotizacionArticuloServiceImplement  implements IItemCotizacionArticuloService{

	@Autowired
	private IItemArticuloDAO itemArticuloDAO;
	
	@Override
	@Transactional
	public void delete(Long id) {
		itemArticuloDAO.deleteById(id);

	}
	@Override
	@Transactional(readOnly = true)
	public ItemCotizacionArticulo findOne(Long id) {
		return itemArticuloDAO.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void save(ItemCotizacionArticulo itemCotizacionArticulo) {
		itemArticuloDAO.save(itemCotizacionArticulo);

	}

}
