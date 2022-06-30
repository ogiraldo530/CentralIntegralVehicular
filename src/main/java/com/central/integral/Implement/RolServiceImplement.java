package com.central.integral.Implement;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.central.integral.entity.Rol;
import com.central.integral.interfaces.IRolDAO;
import com.central.integral.service.IRolService;

@Service
public class RolServiceImplement implements IRolService{
	
	@Autowired
	private IRolDAO rolDAO;

	
	@Override
	@Transactional(readOnly = true)
	public List<Rol> findAll() {

		return (List<Rol>) rolDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Rol rol) {
		rolDAO.save(rol);

	}

}
