package com.central.integral.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.central.integral.entity.Vehiculo;
import com.central.integral.interfaces.IVehiculoDAO;
import com.central.integral.service.IVehiculoService;
@Service
public class VehiculoServiceImplement implements IVehiculoService{
	
	@Autowired
	private IVehiculoDAO vehiculoDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Vehiculo> findAll() {
		return (List<Vehiculo>) vehiculoDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Vehiculo> findAll(Pageable pageable) {
		return vehiculoDAO.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Vehiculo vehiculo) {
		vehiculoDAO.save(vehiculo);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Vehiculo findOne(Long id) {
		return vehiculoDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		vehiculoDAO.deleteById(id);
		
	}

	@Override
	public List<Vehiculo> findByBusqueda(String busqueda) throws Exception {
		try {
			List<Vehiculo> entities = this.vehiculoDAO.findByBusqueda(busqueda);
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
