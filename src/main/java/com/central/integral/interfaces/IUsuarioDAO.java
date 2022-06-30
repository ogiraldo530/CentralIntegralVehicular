package com.central.integral.interfaces;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.central.integral.entity.Usuario;



@Repository
public interface IUsuarioDAO extends PagingAndSortingRepository<Usuario, Long> {
	
	public Usuario findByUsername(String username);
	public Usuario findByRol(String rol);

}
