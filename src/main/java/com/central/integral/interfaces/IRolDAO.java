package com.central.integral.interfaces;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.central.integral.entity.Rol;
import com.central.integral.entity.Usuario;

public interface IRolDAO  extends PagingAndSortingRepository<Rol, Long>{
	
	public Rol findByNombre(String nombre);

}
