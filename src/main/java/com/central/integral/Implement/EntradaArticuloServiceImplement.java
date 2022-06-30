package com.central.integral.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.central.integral.entity.EntradaArticulo;
import com.central.integral.interfaces.IEntradaArticuloDAO;
import com.central.integral.service.IEntradaArticuloService;

@Service
public class EntradaArticuloServiceImplement  implements IEntradaArticuloService{
	
	@Autowired
	private IEntradaArticuloDAO entradaArticuloDAO;
	


	@Override
	@Transactional(readOnly = true)
	public List<EntradaArticulo> findAll() {

		return (List<EntradaArticulo>) entradaArticuloDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<EntradaArticulo> findAll(Pageable pageable) {

		return entradaArticuloDAO.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(EntradaArticulo entradaArticulo) {
		entradaArticuloDAO.save(entradaArticulo);

	}

	@Override
	@Transactional(readOnly = true)
	public EntradaArticulo findOne(Long id) {

		return entradaArticuloDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		entradaArticuloDAO.deleteById(id);

	}

	@Override
	public List<EntradaArticulo> findByBusqueda(String busqueda) throws Exception {
		try {
			List<EntradaArticulo> entities = this.entradaArticuloDAO.findByBusqueda(busqueda);
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}



}
