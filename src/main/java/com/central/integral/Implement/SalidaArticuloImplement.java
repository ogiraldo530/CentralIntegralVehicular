package com.central.integral.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.central.integral.entity.SalidaArticulo;
import com.central.integral.interfaces.ISalidaArticuloDAO;
import com.central.integral.service.ISalidaArticuloService;

@Service
public class SalidaArticuloImplement implements ISalidaArticuloService {
	
	@Autowired
	private ISalidaArticuloDAO salidaArticuloDAO;
	


	@Override
	@Transactional(readOnly = true)
	public List<SalidaArticulo> findAll() {

		return (List<SalidaArticulo>) salidaArticuloDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SalidaArticulo> findAll(Pageable pageable) {

		return salidaArticuloDAO.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(SalidaArticulo SalidaArticulo) {
		salidaArticuloDAO.save(SalidaArticulo);

	}

	@Override
	@Transactional(readOnly = true)
	public SalidaArticulo findOne(Long id) {

		return salidaArticuloDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		salidaArticuloDAO.deleteById(id);

	}

	@Override
	public List<SalidaArticulo> findByBusqueda(String busqueda) throws Exception {
		try {
			List<SalidaArticulo> entities = this.salidaArticuloDAO.findByBusqueda(busqueda);
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}



}
