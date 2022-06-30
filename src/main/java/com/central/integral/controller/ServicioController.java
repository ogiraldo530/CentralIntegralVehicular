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

import com.central.integral.entity.Servicio;
import com.central.integral.paginator.PageRender;
import com.central.integral.service.IServicioService;

@Controller
@SessionAttributes("servicio")
public class ServicioController {
	@Autowired
	private IServicioService servicioService;

	@RequestMapping(value = "/central-integral-vehicular/listarServicio", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Servicio> servicio = servicioService.findAll(pageRequest);

		PageRender<Servicio> pageRender = new PageRender<>("/central-integral-vehicular/listarServicio", servicio);

		model.addAttribute("titulo", "Listado de Servicio");
		model.addAttribute("accion", "listarServicio");
		model.addAttribute("servicio", servicio);
		model.addAttribute("page", pageRender);
		return "servicio/listar";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearServicio")
	public String crear(Map<String, Object> model) {

		Servicio servicio = new Servicio();

		model.put("servicio", servicio);
		model.put("titulo", "Crear servicio");

		return "servicio/crear";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearServicio/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Servicio servicio = null;

		if (id > 0) {
			servicio = servicioService.findOne(id);
			if (servicio == null) {
				flash.addFlashAttribute("error", "¡El ID del servicio no existe en la base de datos!");
				return "redirect:/central-integral-vehicular/listarServicio";
			}
		} else {
			flash.addFlashAttribute("error", "¡El ID del Servicio no puede Ser 0!");
			return "redirect:/central-integral-vehicular/listarServicio";
		}

		model.put("servicio", servicio);

		model.put("titulo", "Editar Servicio");

		return "servicio/crear";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearServicio", method = RequestMethod.POST)
	public String guardar(@Valid Servicio servicio, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Servicio");
			return "/servicio/crear";
		}

		String mensajeFlash = (servicio.getId() != null) ? "¡Servicio editado con exito!" : "¡Servicio Creado con Exito!";

		servicioService.save(servicio);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:listarServicio";
	}

	@RequestMapping(value = "/central-integral-vehicular/eliminarServicio/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model) {

		try {
			if (id > 0) {

				servicioService.delete(id);
				flash.addFlashAttribute("success", "¡Servicio eliminado con Exito!");

			}
			
		} catch (Exception e) {

			model.addAttribute("error", e.getMessage());
			flash.addFlashAttribute("danger", "!Este servicio no se puede eliminar");
			return "redirect:/central-integral-vehicular/listarServicio";
		}


		return "redirect:/central-integral-vehicular/listarServicio";
	}

	@RequestMapping(value = "/central-integral-vehicular/listarServicio/busqueda", method = RequestMethod.GET)
	public String busqueda(Model model, @RequestParam(value = "query", required = false) String busqueda) {

		try {
			List<Servicio> servicio = this.servicioService.findByBusqueda(busqueda);
			model.addAttribute("accion", "listarServicio");
			model.addAttribute("servicio", servicio);
			model.addAttribute("titulo", "Busqueda: " + busqueda);
			model.addAttribute("resultados", "Se encontró " + busqueda + ":");
			model.addAttribute("vacio", "No se encontraron resultados para " + busqueda);
			model.addAttribute("vacioInfo", "Por favor revisar la ortografía, intentelo nuevamente. ");
			return "servicio/busqueda";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "servicio/listar";
		}
	}

}
