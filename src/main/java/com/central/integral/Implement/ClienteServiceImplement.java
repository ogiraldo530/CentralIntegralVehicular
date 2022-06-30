package com.central.integral.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.central.integral.entity.Cliente;
import com.central.integral.interfaces.IClientesDAO;
import com.central.integral.service.IClienteService;

@Service
public class ClienteServiceImplement implements IClienteService {
	@Autowired
	private IClientesDAO clienteDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {

		return (List<Cliente>) clienteDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {

		return clienteDAO.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDAO.save(cliente);

	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {

		return clienteDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDAO.deleteById(id);

	}

	@Override
	public List<Cliente> findByRazonSocial(String busqueda) throws Exception {
		try {
			List<Cliente> entities = this.clienteDAO.findByRazonSocial(busqueda);
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
