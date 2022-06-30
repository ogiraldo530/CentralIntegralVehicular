package com.central.integral.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.central.integral.entity.Servicio;

import com.central.integral.interfaces.IServicioDAO;
import com.central.integral.service.IServicioService;
@Service
public class ServicioServiceImplement implements IServicioService {
	@Autowired
	private IServicioDAO servicioDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findAll() {

		return (List<Servicio>) servicioDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Servicio> findAll(Pageable pageable) {

		return servicioDAO.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Servicio servicio) {
		servicioDAO.save(servicio);

	}

	@Override
	@Transactional(readOnly = true)
	public Servicio findOne(Long id) {

		return servicioDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		servicioDAO.deleteById(id);

	}

	@Override
	public List<Servicio> findByBusqueda(String busqueda) throws Exception {
		try {
			List<Servicio> entities = this.servicioDAO.findByBusqueda(busqueda);
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByDescripcion(String term) {

		return servicioDAO.findByDescripcion(term);
	}

}
