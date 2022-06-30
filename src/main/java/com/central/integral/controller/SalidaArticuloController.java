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
import com.central.integral.entity.SalidaArticulo;
import com.central.integral.entity.Vehiculo;
import com.central.integral.paginator.PageRender;
import com.central.integral.service.IArticuloService;
import com.central.integral.service.ISalidaArticuloService;
import com.central.integral.service.IVehiculoService;

@Controller
@SessionAttributes("SalidaArticulo")
public class SalidaArticuloController {
	
	@Autowired
	private ISalidaArticuloService salidaArticuloService;
	
	@Autowired
	private IArticuloService articuloService;
	
	@Autowired
	private IVehiculoService vehiculoService;
	
	@RequestMapping(value = "/central-integral-vehicular/crearSalidaArticulo")
	public String crearSalida(Map<String, Object> model) {

		List<Vehiculo> vehiculo =  vehiculoService.findAll();
		model.put("vehiculo", vehiculo);
		
		SalidaArticulo salidaArticulo = new SalidaArticulo();

		List<Articulo> articulo = articuloService.findAll();
		model.put("articulo", articulo);
		model.put("salidaArticulo", salidaArticulo);
		model.put("titulo", "Crear Salida de articulo");
		


		return "salida/crear";
	}
	
	@RequestMapping(value = "/central-integral-vehicular/listarSalidaArticulo", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);	

		Page<SalidaArticulo> salidaArticulo = salidaArticuloService.findAll(pageRequest);
		
		PageRender<SalidaArticulo> pageRender = new PageRender<>("/central-integral-vehicular/listarSalidaArticulo", salidaArticulo);

		
		model.addAttribute("titulo", "Listado de Salida de Articulos");
		model.addAttribute("accion", "listarSalidaArticulo");
		model.addAttribute("salidaArticulo", salidaArticulo);
		model.addAttribute("page", pageRender);

		return "salida/listar";
	}
	
	

	@RequestMapping(value = "/central-integral-vehicular/crearSalidaArticulo", method = RequestMethod.POST)
	public String guardarSalida(@Valid SalidaArticulo salidaArticulo, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			List<Articulo> articulo = articuloService.findAll();
			model.addAttribute("articulo", articulo);
			List<Vehiculo> vehiculo =  vehiculoService.findAll();
			model.addAttribute("vehiculo", vehiculo);
			model.addAttribute("titulo", "Crear Salida del Articulo");
			return "/salida/crear";
		}

		String mensajeFlash = (salidaArticulo.getId() != null) ? "¡Salida del articulo editado con exito!"
				: "¡Salida del articulo creada con exito!";

		salidaArticuloService.save(salidaArticulo);
		
		Articulo articulo = null;
		
		if (salidaArticulo.getArticulo() != null) {
			
			articulo = articuloService.findOne(salidaArticulo.getArticulo().getId());
			
			articulo.disminuirStock(salidaArticulo.getCantidad());
			articulo.aumentarSalida(salidaArticulo.getCantidad());
	
		}
		articuloService.save(articulo);
		
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:listarSalidaArticulo";
	}
	@RequestMapping(value = "/central-integral-vehicular/listarSalidaArticulo/busqueda", method = RequestMethod.GET)
	public String busqueda(Model model, @RequestParam(value = "query", required = false) String busqueda) {

		try {
			List<SalidaArticulo> salidaArticulo = this.salidaArticuloService.findByBusqueda(busqueda);
			model.addAttribute("accion", "listarSalidaArticulo");
			model.addAttribute("salidaArticulo", salidaArticulo);
			model.addAttribute("titulo", "Busqueda: " + busqueda);
			model.addAttribute("resultados", "Se encontró " + busqueda + ":");
			model.addAttribute("vacio", "No se encontraron resultados para " + busqueda);
			model.addAttribute("vacioInfo", "Por favor revisar la ortografía, intentelo nuevamente.");
			return "salida/busqueda";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "salida/listar";
		}
	}

}