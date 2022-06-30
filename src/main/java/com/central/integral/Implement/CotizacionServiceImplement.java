package com.central.integral.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.central.integral.entity.Articulo;
import com.central.integral.entity.Cotizacion;
import com.central.integral.entity.ItemCotizacionArticulo;
import com.central.integral.interfaces.ICotizacionDAO;
import com.central.integral.service.ICotizacionService;

@Service
public class CotizacionServiceImplement implements ICotizacionService {

	@Autowired
	private ICotizacionDAO cotizacionDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Cotizacion> findAll() {
		return (List<Cotizacion>) cotizacionDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cotizacion> findAll(Pageable pageable) {
		return cotizacionDAO.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Cotizacion cotizacion) {
		cotizacionDAO.save(cotizacion);

	}

	@Override
	@Transactional(readOnly = true)
	public Cotizacion findOne(Long id) {
		return cotizacionDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		cotizacionDAO.deleteById(id);

	}

	@Override
	public List<Cotizacion> findByBusqueda(String busqueda) throws Exception {
		try {
			List<Cotizacion> entities = this.cotizacionDAO.findByBusqueda(busqueda);
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}


}
