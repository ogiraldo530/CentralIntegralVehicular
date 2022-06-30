package com.central.integral.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.central.integral.entity.OrdenDeTrabajo;

import com.central.integral.interfaces.IOrdenTrabajoDAO;
import com.central.integral.service.IOrdenTrabajoService;

@Service
public class OrdenTrabajoServiceImplement implements IOrdenTrabajoService {
	@Autowired
	private IOrdenTrabajoDAO ordenTrabajoDAO;

	@Override
	@Transactional(readOnly = true)
	public List<OrdenDeTrabajo> findAll() {

		return (List<OrdenDeTrabajo>) ordenTrabajoDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<OrdenDeTrabajo> findAll(Pageable pageable) {

		return ordenTrabajoDAO.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(OrdenDeTrabajo ordenDeTrabajo) {
		ordenTrabajoDAO.save(ordenDeTrabajo);

	}

	@Override
	@Transactional(readOnly = true)
	public OrdenDeTrabajo findOne(Long id) {

		return ordenTrabajoDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		ordenTrabajoDAO.deleteById(id);

	}

	@Override
	public List<OrdenDeTrabajo> findByBusqueda(String busqueda) throws Exception {
		try {
			List<OrdenDeTrabajo> entities = this.ordenTrabajoDAO.findByBusqueda(busqueda);
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
