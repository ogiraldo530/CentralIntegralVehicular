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

import com.central.integral.entity.Herramienta;
import com.central.integral.paginator.PageRender;
import com.central.integral.service.IHerramientaService;

@Controller
@SessionAttributes("herramienta")
public class HerramientaController {

	@Autowired
	private IHerramientaService herramientaService;

	@RequestMapping(value = "/central-integral-vehicular/listarHerramienta", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Herramienta> herramienta = herramientaService.findAll(pageRequest);

		PageRender<Herramienta> pageRender = new PageRender<>("/central-integral-vehicular/listarHerramienta",
				herramienta);

		model.addAttribute("titulo", "Listado de herramientas");
		model.addAttribute("accion", "listarHerramienta");
		model.addAttribute("herramienta", herramienta);
		model.addAttribute("page", pageRender);

		return "herramienta/listar";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearHerramienta")
	public String crear(Map<String, Object> model) {

		Herramienta herramienta = new Herramienta();

		model.put("herramienta", herramienta);
		model.put("titulo", "Crear herramienta");

		return "herramienta/crear";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearHerramienta/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Herramienta herramienta = null;

		if (id > 0) {
			herramienta = herramientaService.findOne(id);
			if (herramienta == null) {
				flash.addFlashAttribute("error", "¡El ID de la herramienta no existe en la base de datos!");
				return "redirect:/central-integral-vehicular/listarHerramienta";
			}
		} else {
			flash.addFlashAttribute("error", "¡El ID de la herramienta no puede se 0!");
			return "redirect:/central-integral-vehicular/listarHerramienta";
		}
		model.put("herramienta", herramienta);
		model.put("titulo", "Editar herramienta");

		return "herramienta/crear";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearHerramienta", method = RequestMethod.POST)
	public String guardar(@Valid Herramienta herramienta, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear herramienta");
			return "/herramienta/crear";
		}

		String mensajeFlash = (herramienta.getId() != null) ? "¡Herramienta editada con exito!"
				: "¡Herramienta creada con exito!";

		herramientaService.save(herramienta);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:listarHerramienta";
	}

	@RequestMapping(value = "/central-integral-vehicular/eliminarHerramienta/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model) {
		try {
			if (id > 0) {

				herramientaService.delete(id);
				flash.addFlashAttribute("success", "herramienta Eliminado con Exito!");
			}

		} catch (Exception e) {

			model.addAttribute("error", e.getMessage());
			flash.addFlashAttribute("danger", "!Este articulo no se puede eliminar porque aparece en otros registros!");
			return "redirect:/central-integral-vehicular/listarHerramienta";

		}

		return "redirect:/central-integral-vehicular/listarHerramienta";
	}

	@RequestMapping(value = "/central-integral-vehicular/listarHerramienta/busqueda", method = RequestMethod.GET)
	public String busqueda(Model model, @RequestParam(value = "query", required = false) String busqueda) {

		try {
			List<Herramienta> herramienta = this.herramientaService.findByBusqueda(busqueda);
			model.addAttribute("accion", "listarHerramienta");
			model.addAttribute("herramienta", herramienta);
			model.addAttribute("titulo", "Busqueda: " + busqueda);
			model.addAttribute("resultados", "Se encontró " + busqueda + ":");
			model.addAttribute("vacio", "No se encontraron resultados para " + busqueda);
			model.addAttribute("vacioInfo", "Por favor revisar la ortografía, intentelo nuevamente.");
			return "herramienta/busqueda";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "herramienta/listar";
		}
	}
}
