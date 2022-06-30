package com.central.integral.Implement;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.central.integral.entity.Herramienta;
import com.central.integral.interfaces.IHerramientaDAO;
import com.central.integral.service.IHerramientaService;

@Service
public class HerramientaServiceImplement implements IHerramientaService{
	@Autowired
	private IHerramientaDAO herramientaDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Herramienta> findAll() {

		return (List<Herramienta>) herramientaDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Herramienta> findAll(Pageable pageable) {

		return herramientaDAO.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Herramienta herramienta) {
		herramientaDAO.save(herramienta);

	}

	@Override
	@Transactional(readOnly = true)
	public Herramienta findOne(Long id) {

		return herramientaDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		herramientaDAO.deleteById(id);

	}

	@Override
	public List<Herramienta> findByBusqueda(String busqueda) throws Exception {
		try {
			List<Herramienta> entities = this.herramientaDAO.findByBusqueda(busqueda);
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}


}
