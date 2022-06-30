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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.central.integral.entity.Cliente;
import com.central.integral.paginator.PageRender;
import com.central.integral.service.IClienteService;


@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;


	@RequestMapping(value = "/central-integral-vehicular/listarCliente", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

	
		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
	
		PageRender<Cliente> pageRender = new PageRender<>("/central-integral-vehicular/listarCliente", clientes);

		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("accion", "listarCliente");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);

		return "cliente/listar";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearCliente")
	public String crear(Map<String, Object> model) {
		
		Cliente cliente = new Cliente();

		model.put("cliente", cliente);
		model.put("titulo", "Crear Cliente");

		return "cliente/crear";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearCliente/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "!El ID del cliente no existe en la base de datos!");
				return "redirect:/central-integral-vehicular/listarCliente";
			}
		} else {
			flash.addFlashAttribute("error", "!El ID del cliente no pueder ser 0!");
			return "redirect:/central-integral-vehicular/listarCliente";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");

		return "cliente/crear";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearCliente", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
		 RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Cliente");
			return "/cliente/crear";
		}
		

		String mensajeFlash = (cliente.getId() != null) ? "!Cliente editado con exito!" : "!Cliente creado con exito!";

		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:listarCliente";
	}
	@RequestMapping(value = "/central-integral-vehicular/eliminarCliente/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model) {
		
		try {
			if (id > 0) {
				
				clienteService.delete(id);
				flash.addFlashAttribute("success", "Cliente eliminado con exito!");

			}	
		}catch (Exception e) {
			
			
			model.addAttribute("error", e.getMessage());
			flash.addFlashAttribute("danger", "!Este cliente no se puede eliminar porque tiene registrados uno o más vechiculos!");
			return "redirect:/central-integral-vehicular/listarCliente";
			
		}
		

		return "redirect:/central-integral-vehicular/listarCliente";
	}
	
	
	@RequestMapping(value = "/central-integral-vehicular/listarCliente/busqueda", method = RequestMethod.GET)
	public String busqueda(Model model, @RequestParam(value = "query", required = false) String busqueda) {
		
		try {
			List<Cliente> clientes = this.clienteService.findByRazonSocial(busqueda);
			model.addAttribute("accion", "listarCliente");
			model.addAttribute("clientes", clientes);
			model.addAttribute("titulo", "Busqueda: " + busqueda);
			model.addAttribute("resultados", "Se encontró " + busqueda + ":");
			model.addAttribute("vacio", "No se encontraron resultados para " + busqueda);
			model.addAttribute("vacioInfo", "Por favor revisar la ortografía");
			return "cliente/busqueda";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "cliente/listar";
		}
	}

}
