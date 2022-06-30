package com.central.integral.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.central.integral.entity.Rol;
import com.central.integral.entity.Usuario;
import com.central.integral.paginator.PageRender;
import com.central.integral.service.IRolService;
import com.central.integral.service.IUsuarioService;



@Controller
public class UserController {
		
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IRolService rolService;
		
	//Metodo para retornar la vista login
	@GetMapping("/login")
	public String formLogin(Model model) {
		model.addAttribute("titulo", "Log In");
		model.addAttribute("usuario", new Usuario());
		
		return "login";
	}
	
	//Metodo para retornar la vista listarAdmin de usuario
	@RequestMapping(value = "/central-integral-vehicular/listarUsuario", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		//Cantidad de datos que se renderizarÃ¡n
		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Usuario> usuarios = usuarioService.findAll(pageRequest);
		
		//Se crear el renderizado de la pagina con el objeto PageRender de tipo usuarios en la URL /admin/listarUsuario
		PageRender<Usuario> pageRender = new PageRender<>("/central-integral-vehicular/listarUsuario", usuarios);
		
		List<Rol> rol =  rolService.findAll();
		model.addAttribute("rol", rol);

		model.addAttribute("titulo", "Listado de Usuarios");
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("page", pageRender);

		return "usuario/listar";
	}
	
	//Metodo para retornar la vista crear de usuario
	@RequestMapping(value = "/central-integral-vehicular/crearUsuario")
	public String crear(Map<String, Object> model) {
		
		List<Rol> rol =  rolService.findAll();


		Usuario usuario = new Usuario();
		model.put("rol", rol);
		model.put("usuario", usuario);
		model.put("titulo", "Crear Usuario");

		return "usuario/crear";
	}
	
	//Metodo para guardar los nuevos usuarios en la BD
	@RequestMapping(value = "/central-integral-vehicular/crearUsuario", method = RequestMethod.POST)
	public String guardar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {

		List<Rol> rol =  rolService.findAll();
		//Captura de errores y redirecciÃ³n a la vista crear de usuario en caso de que sucedan
		if (result.hasErrors()) {
			model.addAttribute("rol", rol);
			model.addAttribute("titulo", "Crear Usuario");
			return "/usuario/crear";
		}

		String mensajeFlash = (usuario.getId() != null) ? "Usuario Editado con Exito!" : "Usuario Creado con Exito!";

		usuarioService.save(usuario);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:listarUsuario";
	}
	
	//Metodo para editar un uusario creado con anterioridad
	@RequestMapping(value = "/central-integral-vehicular/crearUsuario/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Usuario usuario = null;

		if (id > 0) {
			usuario = usuarioService.findOne(id);
			if (usuario == null) {
				flash.addFlashAttribute("error", "El ID del Usuario NO Existe en la BBDD!");
				return "redirect:/central-integral-vehicular/listarUsuario";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del Usuario NO Pueder Ser 0!");
			return "redirect:/central-integral-vehicular/listarUsuario";
		}
		List<Rol> rol =  rolService.findAll();
		model.put("rol", rol);

		model.put("usuario", usuario);
		model.put("titulo", "Editar Usuario");

		return "usuario/crear";
	}
	
	//Metodo para eliminar un usuario
	@RequestMapping(value = "/central-integral-vehicular/eliminarUsuario/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			usuarioService.delete(id);
			flash.addFlashAttribute("success", "Usuario Eliminado con Exito!");
		}

		return "redirect:/central-integral-vehicular/listarUsuario";
	}
	
}
