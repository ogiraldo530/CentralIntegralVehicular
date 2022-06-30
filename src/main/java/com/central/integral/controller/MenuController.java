package com.central.integral.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.central.integral.entity.Usuario;
import com.central.integral.service.IUsuarioService;

@Controller
public class MenuController {
	
	
	@Autowired
	private IUsuarioService usuarioService;
	
	
	@GetMapping("/menu")
	public String admin(Model model, Authentication auth, HttpSession session) {
		model.addAttribute("titulo", "Admin");
		
		String username = auth.getName();
		
		if(session.getAttribute("usuario") == null) {
			Usuario usuario = usuarioService.findByUsername(username);
			usuario.setPassword(null);
			session.setAttribute("usuario", usuario);
		}
		model.addAttribute("titulo", "Menu");
		
		return "menu";
	}

}
