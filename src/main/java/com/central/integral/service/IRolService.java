package com.central.integral.service;

import java.util.List;

import com.central.integral.entity.Cliente;
import com.central.integral.entity.Rol;

public interface IRolService {	
	public List<Rol> findAll();
	public void save(Rol rol);

}
