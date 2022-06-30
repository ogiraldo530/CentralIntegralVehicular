package com.central.integral.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.central.integral.entity.Articulo;
import com.central.integral.interfaces.IArticuloDAO;
import com.central.integral.service.IArticuloService;

@Service
public class ArticuloServiceImplement implements IArticuloService {
	@Autowired
	private IArticuloDAO articuloDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Articulo> findAll() {

		return (List<Articulo>) articuloDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Articulo> findAll(Pageable pageable) {

		return articuloDAO.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Articulo articulo) {
		articuloDAO.save(articulo);

	}

	@Override
	@Transactional(readOnly = true)
	public Articulo findOne(Long id) {

		return articuloDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		articuloDAO.deleteById(id);

	}

	@Override
	public List<Articulo> findByBusqueda(String busqueda) throws Exception {
		try {
			List<Articulo> entities = this.articuloDAO.findByBusqueda(busqueda);
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Articulo> findByDescripcion(String term) {
		
		return articuloDAO.findByDescripcion(term);
	}

}
