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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.central.integral.entity.Cliente;
import com.central.integral.entity.Vehiculo;
import com.central.integral.paginator.PageRender;
import com.central.integral.service.IClienteService;
import com.central.integral.service.IVehiculoService;

@Controller
@SessionAttributes("vehiculo")
public class VehiculoController {

	@Autowired
	private IVehiculoService vehiculoService;

	@Autowired
	private IClienteService clienteService;

	@GetMapping(value = "/central-integral-vehicular/verVehiculo/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Vehiculo vehiculo = vehiculoService.findOne(id);

		if (vehiculo == null) {
			flash.addFlashAttribute("error", "El Vehiculo NO Existe en la Base de Datos");
			return "redirect:/listarVehiculo";
		}

		model.put("vehiculo", vehiculo);
		model.put("titulo", "Detalle vehiculo: " + vehiculo.getPlaca());

		return "vehiculo/ver";
	}

	@GetMapping(value = "/central-integral-vehicular/verHistorialVehicular/{id}")
	public String verHistorial(@PathVariable(value = "id") Long id, Map<String, Object> model,
			RedirectAttributes flash) {

		Vehiculo vehiculo = vehiculoService.findOne(id);

		if (vehiculo == null) {
			flash.addFlashAttribute("error", "El Vehiculo no existe en la base de datos");
			return "redirect:/listarVehiculo";
		}

		model.put("vehiculo", vehiculo);
		model.put("titulo", "Historial vehiculo: " + vehiculo.getPlaca());

		return "vehiculo/historial";
	}

	@RequestMapping(value = "/central-integral-vehicular/listarVehiculo", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Vehiculo> vehiculos = vehiculoService.findAll(pageRequest);

		PageRender<Vehiculo> pageRender = new PageRender<>("/central-integral-vehicular/listarVehiculo", vehiculos);

		List<Cliente> cliente = clienteService.findAll();
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Listado de vehiculos");
		model.addAttribute("accion", "listarVehiculo");
		model.addAttribute("vehiculos", vehiculos);
		model.addAttribute("page", pageRender);

		return "vehiculo/listar";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearVehiculo/{id}")
	public String crear(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(id);

		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente NO Existe en la Base de Datos!");
			return "redirect:/central-integral-vehicular/listarCliente";
		}

		Vehiculo vehiculo = new Vehiculo();

		vehiculo.setCliente(cliente);

		model.put("vehiculo", vehiculo);
		model.put("titulo", "Crear vehiculo");

		return "vehiculo/crear";
	}

	@RequestMapping(value = "/central-integral-vehicular/editarVehiculo/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Vehiculo vehiculo = null;

		if (id > 0) {
			vehiculo = vehiculoService.findOne(id);
			if (vehiculo == null) {
				flash.addFlashAttribute("error", "!El ID del vehiculo no existe en la base de datos!");
				return "redirect:/central-integral-vehicular/listarVehiculo";
			}
		} else {
			flash.addFlashAttribute("error", "!El ID del vehiculo no puede ser 0!");
			return "redirect:/central-integral-vehicular/listarVehiculo";
		}
		List<Cliente> cliente = clienteService.findAll();
		model.put("cliente", cliente);
		model.put("vehiculo", vehiculo);
		model.put("titulo", "Editar vehiculo");

		return "vehiculo/editar";
	}

	@RequestMapping(value = "/central-integral-vehicular/crearVehiculo", method = RequestMethod.POST)
	public String guardar(@Valid Vehiculo vehiculo, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear vehiculo");
			return "/vehiculo/crear";
		}

		String mensajeFlash = (vehiculo.getId() != null) ? "!Vehiculo editado con exito!"
				: "!Vehiculo creado con exito!";

		vehiculoService.save(vehiculo);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:listarVehiculo";
	}

	@RequestMapping(value = "/central-integral-vehicular/editarVehiculo", method = RequestMethod.POST)
	public String guardarEditar(@Valid Vehiculo vehiculo, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear vehiculo");
			return "/vehiculo/editar";
		}

		String mensajeFlash = (vehiculo.getId() != null) ? "!Vehiculo editado con exito!"
				: "!Vehiculo creado con exito!";

		vehiculoService.save(vehiculo);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:listarVehiculo";
	}

	@RequestMapping(value = "/central-integral-vehicular/eliminarVehiculo/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model) {
		try {
			if (id > 0) {

				vehiculoService.delete(id);
				flash.addFlashAttribute("success", "Vehiculo eliminado con exito!");

			}
		} catch (Exception e) {

			model.addAttribute("error", e.getMessage());
			flash.addFlashAttribute("danger", "!Este vehiculo tiene cotizaciones registradas");
			return "redirect:/central-integral-vehicular/listarVehiculo";

		}

		return "redirect:/central-integral-vehicular/listarVehiculo";
	}

	@RequestMapping(value = "/central-integral-vehicular/listarVehiculo/busqueda", method = RequestMethod.GET)
	public String busqueda(Model model, @RequestParam(value = "query", required = false) String busqueda) {

		try {
			List<Vehiculo> vehiculos = this.vehiculoService.findByBusqueda(busqueda);
			model.addAttribute("vehiculos", vehiculos);
			model.addAttribute("accion", "listarVehiculo");
			model.addAttribute("titulo", "Busqueda: " + busqueda);
			model.addAttribute("resultados", "Resultados relacionados con: " + busqueda);
			model.addAttribute("vacio", "No se encontraron resultados para " + busqueda);
			model.addAttribute("vacioInfo", "Por favor revisar la ortografía, intentelo nuevamente.");
			return "vehiculo/busqueda";
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "vehiculo/listar";
		}
	}

}
