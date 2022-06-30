package com.central.integral.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.central.integral.entity.Usuario;


public interface IUsuarioService {
	

	public List<Usuario> findAll();
	
	public Page<Usuario> findAll(Pageable pageable);
	
	public Usuario findOne(Long id);
	
	public Usuario findByUsername(String username);
	
	public Usuario findByRol(String rol);
	
	public void save(Usuario usuario);

	public void delete(Long id);
}
