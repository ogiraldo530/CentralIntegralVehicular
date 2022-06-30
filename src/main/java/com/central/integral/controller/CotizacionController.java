package com.central.integral.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.central.integral.entity.Articulo;
import com.central.integral.entity.Cotizacion;
import com.central.integral.entity.ItemCotizacionArticulo;
import com.central.integral.entity.ItemCotizacionServicio;
import com.central.integral.entity.Servicio;
import com.central.integral.entity.Vehiculo;
import com.central.integral.service.IArticuloService;
import com.central.integral.service.ICotizacionService;
import com.central.integral.service.IItemCotizacionArticuloService;
import com.central.integral.service.IItemCotizacionServicioService;
import com.central.integral.service.IServicioService;
import com.central.integral.service.IVehiculoService;

@Controller
@SessionAttributes("cotizacion")
public class CotizacionController {

	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IArticuloService articuloService;
	@Autowired
	private IServicioService servicioService;
	@Autowired
	private ICotizacionService cotizacionService;
	@Autowired
	private IItemCotizacionArticuloService itemArticuloService;
	@Autowired
	private IItemCotizacionServicioService itemServicioService;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping(value = "/central-integral-vehicular/verCotizacion/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cotizacion cotizacion = cotizacionService.findOne(id);

		if (cotizacion == null) {
			flash.addFlashAttribute("error", "La cotizacion NO Existe en la Base de Datos");
			return "redirect:/listarCotizacion";
		}

		model.put("cotizacion", cotizacion);
		model.put("titulo", "CENTRAL INTEGRAL VEHÍCULAR SAS NIT. 901 319 875 - 7");

		return "cotizacion/ver";
	}

	@GetMapping(value = "/central-integral-vehicular/crearCotizacion/{id}")
	public String crear(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Vehiculo vehiculo = vehiculoService.findOne(id);

		if (vehiculo == null) {
			flash.addFlashAttribute("error", "El vehiculo no existe en la Base de Datos!");
			return "redirect:/central-integral-vehicular/listarVehiculo";
		}

		Cotizacion cotizacion = new Cotizacion();
		cotizacion.setVehiculo(vehiculo);

		model.put("cotizacion", cotizacion);
		model.put("titulo", "Crear Cotizacion");

		return "cotizacion/crear";
	}

	@PostMapping("/central-integral-vehicular/crearCotizacion")
	public String guardar(Cotizacion cotizacion, BindingResult result, Model model,
			@RequestParam(name = "itemArticulo_id[]", required = false) Long[] itemArticulo,
			@RequestParam(name = "cantidadArticulo[]", required = false) Integer[] cantidadArticulo,
			@RequestParam(name = "descuentoArticulo[]", required = false) Integer[] descuentoArticulo,
			@RequestParam(name = "ivaArticulo[]", required = false) Integer[] ivaArticulo,
			@RequestParam(name = "itemServicio_id[]", required = false) Long[] itemServicio,
			@RequestParam(name = "cantidadServicio[]", required = false) Integer[] cantidadServicio,
			@RequestParam(name = "descuentoServicio[]", required = false) Integer[] descuentoServicio,
			@RequestParam(name = "ivaServicio[]", required = false) Integer[] ivaServicio, RedirectAttributes flash,
			SessionStatus status) {

		if (itemArticulo == null) {
			cotizacionService.save(cotizacion);
			status.setComplete();

		} else {
			for (int i = 0; i < itemArticulo.length; i++) {
				Articulo articulo = articuloService.findOne(itemArticulo[i]);

				ItemCotizacionArticulo linea = new ItemCotizacionArticulo();

				linea.setCotizacion(cotizacion);
				linea.setCantidad(cantidadArticulo[i]);
				linea.setDescuento(descuentoArticulo[i]);
				linea.setIva(ivaArticulo[i]);
				linea.setArticulo(articulo);
				cotizacion.addItemCotizacionArticulo(linea);
				log.info("Articulo --  ID: " + itemArticulo[i].toString() + ", cantidad: "
						+ cantidadArticulo[i].toString());

			}
		}

		if (itemServicio == null) {

			cotizacionService.save(cotizacion);
			status.setComplete();
		} else {

			for (int i = 0; i < itemServicio.length; i++) {
				Servicio servicio = servicioService.findOne(itemServicio[i]);

				ItemCotizacionServicio linea2 = new ItemCotizacionServicio();
				linea2.setCotizacion(cotizacion);
				linea2.setCantidad(cantidadServicio[i]);
				linea2.setDescuento(descuentoServicio[i]);
				linea2.setIva(ivaServicio[i]);
				linea2.setServicio(servicio);
				cotizacion.addItemCotizacionServicio(linea2);

				log.info("Servicio --  ID: " + itemServicio[i].toString() + ", cantidad: "
						+ cantidadServicio[i].toString());
			}
		}

		cotizacionService.save(cotizacion);
		status.setComplete();

		flash.addFlashAttribute("success", "Cotización creada con éxito!");

		return "redirect:/central-integral-vehicular/verVehiculo/" + cotizacion.getVehiculo().getId();

	}

	@RequestMapping(value = "/central-integral-vehicular/editarCotizacion/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cotizacion cotizacion = null;

		if (id > 0) {
			cotizacion = cotizacionService.findOne(id);
			if (cotizacion == null) {
				flash.addFlashAttribute("error", "El ID de la cotizacion NO Existe en la BBDD!");
				return "redirect:/central-integral-vehicular/verVehiculo";
			}
		} else {
			flash.addFlashAttribute("error", "El ID de la cotizacion NO Pueder Ser 0!");
			return "redirect:/central-integral-vehicular/verVehiculo";
		}
		model.put("cotizacion", cotizacion);
		model.put("titulo", "Editar Cotizacion");

		return "cotizacion/editar";
	}

	@GetMapping(value = "/central-integral-vehicular/cargar-articulos/{term}", produces = { "application/json" })
	public @ResponseBody List<Articulo> cargarArticulos(@PathVariable String term) {

		return articuloService.findByDescripcion(term);
	}

	@GetMapping(value = "/central-integral-vehicular/cargar-servicios/{term2}", produces = { "application/json" })
	public @ResponseBody List<Servicio> cargarServicios(@PathVariable String term2) {

		return servicioService.findByDescripcion(term2);
	}

	@PostMapping("/central-integral-vehicular/editarCotizacion")
	public String guardarEditar(Cotizacion cotizacion,

			BindingResult result, Model model,
			@RequestParam(name = "itemArticulo_id[]", required = false) Long[] itemArticulo,
			@RequestParam(name = "cantidadArticulo[]", required = false) Integer[] cantidadArticulo,
			@RequestParam(name = "descuentoArticulo[]", required = false) Integer[] descuentoArticulo,
			@RequestParam(name = "ivaArticulo[]", required = false) Integer[] ivaArticulo,
			@RequestParam(name = "itemServicio_id[]", required = false) Long[] itemServicio,
			@RequestParam(name = "cantidadServicio[]", required = false) Integer[] cantidadServicio,
			@RequestParam(name = "descuentoServicio[]", required = false) Integer[] descuentoServicio,
			@RequestParam(name = "ivaServicio[]", required = false) Integer[] ivaServicio, RedirectAttributes flash,
			SessionStatus status) {

		if (itemArticulo == null) {
			cotizacionService.save(cotizacion);
			status.setComplete();

		} else {
			for (int i = 0; i < itemArticulo.length; i++) {

				Articulo articulo = articuloService.findOne(itemArticulo[i]);

				ItemCotizacionArticulo linea = new ItemCotizacionArticulo();

				linea.setCotizacion(cotizacion);

				linea.setCantidad(cantidadArticulo[i]);
				linea.setCantidad(cantidadArticulo[i]);
				linea.setDescuento(descuentoArticulo[i]);
				linea.setIva(ivaArticulo[i]);
				linea.setArticulo(articulo);
				cotizacion.addItemCotizacionArticulo(linea);

				log.info("Articulo --  ID: " + itemArticulo[i].toString() + ", cantidad: "
						+ cantidadArticulo[i].toString());

			}
		}

		if (itemServicio == null) {

			cotizacionService.save(cotizacion);
			status.setComplete();
		} else {

			for (int i = 0; i < itemServicio.length; i++) {
				Servicio servicio = servicioService.findOne(itemServicio[i]);

				ItemCotizacionServicio linea2 = new ItemCotizacionServicio();

				linea2.setCotizacion(cotizacion);
				linea2.setCantidad(cantidadServicio[i]);
				linea2.setDescuento(descuentoServicio[i]);
				linea2.setIva(ivaServicio[i]);
				linea2.setServicio(servicio);

				cotizacion.addItemCotizacionServicio(linea2);

				log.info("Servicio --  ID: " + itemServicio[i].toString() + ", cantidad: "
						+ cantidadServicio[i].toString());
			}
		}

		cotizacionService.save(cotizacion);
		status.setComplete();

		flash.addFlashAttribute("success", "Cotización editada con éxito!");

		return "redirect:/central-integral-vehicular/verVehiculo/" + cotizacion.getVehiculo().getId();

	}

	@RequestMapping(value = "/central-integral-vehicular/eliminarCotizacion/{idCotizacion}/{idVehiculo}")
	public String eliminar(@PathVariable(value = "idCotizacion") Long idCotizacion,
			@PathVariable(value = "idVehiculo") Long idVehiculo, RedirectAttributes flash, Model model) {
		try {
			if (idCotizacion > 0) {

				cotizacionService.delete(idCotizacion);
				flash.addFlashAttribute("success", "Cotización eliminada con Exito!");

			}
		} catch (Exception e) {

			model.addAttribute("error", e.getMessage());
			flash.addFlashAttribute("danger", "!Esta linea no se puede eliminar!");
			return "redirect:/central-integral-vehicular/listarArticulo";

		}

		return "redirect:/central-integral-vehicular/verVehiculo/" + idVehiculo;
	}

	@RequestMapping(value = "/central-integral-vehicular/eliminarItemCotizacionArticulo/{idArticulo}/{idItem}/{idCotizacion}")
	public String eliminarItemArticulo(@PathVariable(value = "idArticulo") Long idArticulo,
			@PathVariable(value = "idItem") Long idItem, @PathVariable(value = "idCotizacion") Long idCotizacion,
			RedirectAttributes flash, Model model) {
		try {
			if (idArticulo > 0) {

				itemArticuloService.delete(idItem);
				flash.addFlashAttribute("success", "Linea eliminada con Exito!");

			}
		} catch (Exception e) {

			model.addAttribute("error", e.getMessage());
			flash.addFlashAttribute("danger", "!Esta linea no se puede eliminar!");
			return "redirect:/central-integral-vehicular/editarCotizacion/" + idCotizacion;

		}

		return "redirect:/central-integral-vehicular/editarCotizacion/" + idCotizacion;
	}

	@RequestMapping(value = "/central-integral-vehicular/eliminarItemCotizacionServicio/{idServicio}/{idItem}/{idCotizacion}")
	public String eliminarItemServicio(@PathVariable(value = "idServicio") Long idServicio,
			@PathVariable(value = "idItem") Long idItem, @PathVariable(value = "idCotizacion") Long idCotizacion,
			RedirectAttributes flash, Model model) {
		try {
			if (idServicio > 0) {

				itemServicioService.delete(idItem);
				flash.addFlashAttribute("success", "Linea Eliminada con Exito!");

			}
		} catch (Exception e) {

			model.addAttribute("error", e.getMessage());
			flash.addFlashAttribute("danger", "!Esta linea no se puede eliminar");
			return "redirect:/central-integral-vehicular/editarCotizacion/" + idCotizacion;
		}

		return "redirect:/central-integral-vehicular/editarCotizacion/" + idCotizacion;
	}

	@RequestMapping(value = "/central-integral-vehicular/cerrarCotizacion/{id}")
	public String cerrar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		Cotizacion cotizacion = null;

		if (id > 0) {
			cotizacion = cotizacionService.findOne(id);
			if (cotizacion == null) {
				flash.addFlashAttribute("error", "El ID de la cotizacion NO Existe en la BBDD!");
				return "redirect:/central-integral-vehicular/verVehiculo";
			}
		} else {
			flash.addFlashAttribute("error", "El ID de la cotizacion NO Pueder Ser 0!");
			return "redirect:/central-integral-vehicular/verVehiculo";
		}

		cotizacion.setEstado("Cerrado");

		cotizacionService.save(cotizacion);

		return "redirect:/central-integral-vehicular/listarOrdenDeTrabajo";
	}

}
