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

import com.central.integral.entity.Articulo;
import com.central.integral.paginator.PageRender;
import com.central.integral.service.IArticuloService;

@Controller
@SessionAttributes("articulo")
public class ArticuloController {
	@Autowired
	private IArticuloService articuloService;


	@RequestMapping(value = "/central-integral-vehicular/listarArticulo", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Articulo> articulo = articuloService.findAll(pageRequest);

		PageRender<Articulo> pageRender = new PageRender<>("/central-integral-vehicular/listarArticulo", articulo);

		model.addAttribute("titulo", "Listado de Articulos");
		model.addAttribute("accion", "listarArticulo");
		model.addAttribute("articulo", articulo);
		model.addAttribute("page", pageRender);
		return "articulo/listar";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearArticulo")
	public String crear(Map<String, Object> model) {

		Articulo articulo = new Articulo();

		model.put("articulo", articulo);
		model.put("titulo", "Crear Articulos");

		return "articulo/crear";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearArticulo/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Articulo articulo = null;

		if (id > 0) {
			articulo = articuloService.findOne(id);
			if (articulo == null) {
				flash.addFlashAttribute("error", "¡El ID del articulo no existe en la base de datos!");
				return "redirect:/central-integral-vehicular/listarArticulo";
			}
		} else {
			flash.addFlashAttribute("error", "¡El ID del articulo no pueder ser 0!");
			return "redirect:/central-integral-vehicular/listarArticulo";
		}

		model.put("articulo", articulo);

		model.put("titulo", "Editar Articulo");

		return "articulo/crear";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearArticulo", method = RequestMethod.POST)
	public String guardar(@Valid Articulo articulo, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Articulo");
			return "/articulo/crear";
		}

		String mensajeFlash = (articulo.getId() != null) ? "Articulo editado con exito!" : "Articulo creado con exito!";

		articuloService.save(articulo);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:listarArticulo";
	}

	@RequestMapping(value = "/central-integral-vehicular/eliminarArticulo/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model) {
		try {
		if (id > 0) {

			articuloService.delete(id);
			flash.addFlashAttribute("success", "¡Articulo eliminado con exito!");

		}
		}catch (Exception e) {
			
			
			model.addAttribute("error", e.getMessage());
			flash.addFlashAttribute("danger", "!Este articulo no se puede eliminar porque aparece en otros registros!");
			return "redirect:/central-integral-vehicular/listarArticulo";
			
		}
		

		return "redirect:/central-integral-vehicular/listarArticulo";
	}

	@RequestMapping(value = "/central-integral-vehicular/listarArticulo/busqueda", method = RequestMethod.GET)
	public String busqueda(Model model, @RequestParam(value = "query", required = false) String busqueda) {

		try {
			List<Articulo> articulo = this.articuloService.findByBusqueda(busqueda);
			model.addAttribute("accion", "listarArticulo");
			model.addAttribute("articulo", articulo);
			model.addAttribute("titulo", "Busqueda: " + busqueda);
			model.addAttribute("resultados", "Se encontró " + busqueda + ":");
			model.addAttribute("vacio", "No se encontraron resultados para " + busqueda);
			model.addAttribute("vacioInfo", "Por favor revisar la ortografía, intentelo nuevamente");
			return "articulo/busqueda";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "articulo/listar";
		}
	}

}
