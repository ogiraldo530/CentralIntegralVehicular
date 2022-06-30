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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.central.integral.entity.Articulo;
import com.central.integral.entity.EntradaArticulo;
import com.central.integral.paginator.PageRender;
import com.central.integral.service.IArticuloService;
import com.central.integral.service.IEntradaArticuloService;

@Controller
@SessionAttributes("entradaArticulo")
public class EntradaArticuloController {
	
	@Autowired
	private IEntradaArticuloService entradaArticuloService;
	
	@Autowired
	private IArticuloService articuloService;
	
	@RequestMapping(value = "/central-integral-vehicular/crearEntradaArticulo")
	public String crearEntrada(Map<String, Object> model) {

		EntradaArticulo entradaArticulo = new EntradaArticulo();

		List<Articulo> articulo = articuloService.findAll();
		model.put("articulo", articulo);
		model.put("entradaArticulo", entradaArticulo);
		model.put("titulo", "Crear entrada de articulo");
		


		return "entrada/crear";
	}
	
	@RequestMapping(value = "/central-integral-vehicular/listarEntradaArticulo", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 5);	

		Page<EntradaArticulo> entradaArticulo = entradaArticuloService.findAll(pageRequest);
		
		PageRender<EntradaArticulo> pageRender = new PageRender<>("/central-integral-vehicular/listarEntradaArticulo", entradaArticulo);

		model.addAttribute("titulo", "Listado de entrada de articulos");
		model.addAttribute("accion", "listarEntradaArticulo");
		model.addAttribute("entradaArticulo", entradaArticulo);
		model.addAttribute("page", pageRender);

		return "entrada/listar";
	}
	
	

	@RequestMapping(value = "/central-integral-vehicular/crearEntradaArticulo", method = RequestMethod.POST)
	public String guardarEntrada(@Valid EntradaArticulo entradaArticulo, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			List<Articulo> articulo = articuloService.findAll();
			model.addAttribute("articulo", articulo);
			model.addAttribute("titulo", "Crear entrada del articulo");
			return "/entrada/crear";
		}

		String mensajeFlash = (entradaArticulo.getId() != null) ? "¡Entrada del articulo editado con exito!"
				: "Entrada del erticulo creado con exito!";

		entradaArticuloService.save(entradaArticulo);
		
		Articulo articulo = null;
		
		if (entradaArticulo.getArticulo() != null) {
			
			articulo = articuloService.findOne(entradaArticulo.getArticulo().getId());
			
			articulo.aumentarStock(entradaArticulo.getCantidad());
			articulo.aumentarEntrada(entradaArticulo.getCantidad());
	
		}
		articuloService.save(articulo);
		
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:listarEntradaArticulo";
	}
	@RequestMapping(value = "/central-integral-vehicular/listarEntradaArticulo/busqueda", method = RequestMethod.GET)
	public String busqueda(Model model, @RequestParam(value = "query", required = false) String busqueda) {

		try {
			List<EntradaArticulo> entradaArticulo = this.entradaArticuloService.findByBusqueda(busqueda);
			model.addAttribute("accion", "listarEntradaArticulo");
			model.addAttribute("entradaArticulo", entradaArticulo);
			model.addAttribute("titulo", "Busqueda: " + busqueda);
			model.addAttribute("resultados", "Se encontró " + busqueda + ":");
			model.addAttribute("vacio", "No se encontraron resultados para " + busqueda);
			model.addAttribute("vacioInfo", "Por favor revisar la ortografía, intentelo nuevamente.");
			return "entrada/busqueda";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "entrada/listar";
		}
	}


}
