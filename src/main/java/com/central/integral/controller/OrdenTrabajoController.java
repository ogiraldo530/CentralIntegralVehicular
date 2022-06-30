package com.central.integral.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.central.integral.entity.Cotizacion;
import com.central.integral.entity.OrdenDeTrabajo;
import com.central.integral.paginator.PageRender;
import com.central.integral.service.ICotizacionService;
import com.central.integral.service.IOrdenTrabajoService;

@Controller
@SessionAttributes("orden_de_trabajo")
public class OrdenTrabajoController {

	@Autowired
	private IOrdenTrabajoService ordenTrabajoService;

	@Autowired
	private ICotizacionService cotizacionService;

	@GetMapping(value = "/central-integral-vehicular/verOrdenDeTrabajo/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		OrdenDeTrabajo ordenTrabajo = ordenTrabajoService.findOne(id);

		if (ordenTrabajo == null) {
			flash.addFlashAttribute("error", "La orden de trabajo no existe en la base de datos");
			return "redirect:/listarOrdenDeTrabajo";
		}

		model.put("ordenTrabajo", ordenTrabajo);
		model.put("titulo", "Detalle Orden de trabajo: " + ordenTrabajo.getId());

		return "ordenTrabajo/ver";
	}

	@RequestMapping(value = "/central-integral-vehicular/listarOrdenDeTrabajo", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<OrdenDeTrabajo> ordenTrabajo = ordenTrabajoService.findAll(pageRequest);

		PageRender<OrdenDeTrabajo> pageRender = new PageRender<>("/central-integral-vehicular/listarOrdenDeTrabajo",
				ordenTrabajo);

		model.addAttribute("titulo", "Listado de ordenes de trabajo");
		model.addAttribute("accion", "listarOrdenDeTrabajo");
		model.addAttribute("ordenTrabajo", ordenTrabajo);
		model.addAttribute("page", pageRender);

		return "ordenTrabajo/listar";
	}

	@GetMapping(value = "/central-integral-vehicular/crearOrdenDeTrabajo/{id}")
	public String crear(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cotizacion cotizacion = cotizacionService.findOne(id);

		OrdenDeTrabajo ordenTrabajo = new OrdenDeTrabajo();

		ordenTrabajo.setCotizacion(cotizacion);

		model.put("ordenTrabajo", ordenTrabajo);
		model.put("titulo", "Crear orden de trabajo");

		return "ordenTrabajo/crear";
	}

	@PostMapping("/central-integral-vehicular/crearOrdenDeTrabajo")
	public String guardar(OrdenDeTrabajo ordenTrabajo, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		Cotizacion cotizacion = cotizacionService.findOne(ordenTrabajo.getCotizacion().getId());

		if (cotizacion == null) {
			flash.addFlashAttribute("error", "La Cotización no existe en la base de datos!");
			return "redirect:/central-integral-vehicular/listarOrdenDeTrabajo";
		}

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Orden de Trabajo");
			return "/ordenTrabajo/crear";
		}

		cotizacion.setEstado("Enviado");

		cotizacionService.save(cotizacion);

		String mensajeFlash = (ordenTrabajo.getId() != null) ? "Orden de Trabajo Editada con Exito!"
				: "Orden de Trabajo  Creado con Exito!";

		ordenTrabajoService.save(ordenTrabajo);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:/central-integral-vehicular/listarOrdenDeTrabajo";
	}

	@RequestMapping(value = "/central-integral-vehicular/editarOrdenDeTrabajo/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		OrdenDeTrabajo ordenTrabajo = null;

		if (id > 0) {
			ordenTrabajo = ordenTrabajoService.findOne(id);
			if (ordenTrabajo == null) {
				flash.addFlashAttribute("error", "El ID de la orden de trabajo NO Existe en la BBDD!");
				return "redirect:/central-integral-vehicular/listarOrdenDeTrabajo";
			}
		} else {
			flash.addFlashAttribute("error", "El ID de la orden de trabajo NO Pueder Ser 0!");
			return "redirect:/central-integral-vehicular/listarOrdenDeTrabajo";
		}

		model.put("ordenTrabajo", ordenTrabajo);
		model.put("titulo", "Editar Orden de trabajo");

		return "ordenTrabajo/editar";
	}

	@RequestMapping(value = "/central-integral-vehicular/editarOrdenDeTrabajo", method = RequestMethod.POST)
	public String guardarEditar(OrdenDeTrabajo ordenTrabajo, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Orden de Trabajo");
			return "/ordenTrabajo/editar";
		}

		ordenTrabajoService.save(ordenTrabajo);
		status.setComplete();
		flash.addFlashAttribute("success", "Orden editada");

		return "redirect:/central-integral-vehicular/listarOrdenDeTrabajo";
	}

	@RequestMapping(value = "/central-integral-vehicular/eliminarOrdenDeTrabajo/{idOrden}/{idCotizacion}")
	public String eliminar(@PathVariable(value = "idOrden") Long idOrden,
			@PathVariable(value = "idCotizacion") Long idCotizacion, RedirectAttributes flash, Model model) {
		Cotizacion cotizacion = cotizacionService.findOne(idCotizacion);
		try {
			if (idOrden > 0) {

				ordenTrabajoService.delete(idOrden);
				cotizacion.setEstado("Abierto");
				cotizacionService.save(cotizacion);
				flash.addFlashAttribute("success", "Orden Eliminada con Exito!");

			}
		} catch (Exception e) {

			model.addAttribute("error", e.getMessage());
			flash.addFlashAttribute("danger", "!Este articulo no se puede eliminar porque aparece en otros registros!");
			return "redirect:/central-integral-vehicular/listarOrdenDeTrabajo";

		}

		return "redirect:/central-integral-vehicular/listarOrdenDeTrabajo";
	}

	@RequestMapping(value = "/central-integral-vehicular/listarOrdenDeTrabajo/busqueda", method = RequestMethod.GET)
	public String busqueda(Model model, @RequestParam(value = "query", required = false) String busqueda) {

		try {
			List<OrdenDeTrabajo> ordenTrabajo = this.ordenTrabajoService.findByBusqueda(busqueda);
			model.addAttribute("ordenTrabajo", ordenTrabajo);
			model.addAttribute("accion", "listarOrdenDeTrabajo");
			model.addAttribute("titulo", "Busqueda: " + busqueda);
			model.addAttribute("resultados", "Resultados relacionados con: " + busqueda);
			model.addAttribute("vacio", "No se encontraron resultados para " + busqueda);
			model.addAttribute("vacioInfo", "Por favor revisar la ortografía, intentelo nuevamente. ");
			return "ordenTrabajo/busqueda";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "ordenTrabajo/listar";
		}
	}

}
