package com.central.integral.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "cotizacion")
public class Cotizacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaVencimiento = null;

	@Temporal(TemporalType.TIME)
	private Date hora;

	private String formaPago;

	private String estado = "Abierto";

	@ManyToOne(fetch = FetchType.LAZY)
	private Vehiculo vehiculo;

	@OneToMany(mappedBy = "cotizacion", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemCotizacionArticulo> items;

	@OneToMany(mappedBy = "cotizacion", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemCotizacionServicio> itemsServicio;

	@OneToOne(mappedBy = "cotizacion")
	private OrdenDeTrabajo ordenDeTrabajo;

	public Cotizacion() {
		this.items = new ArrayList<ItemCotizacionArticulo>();
		this.itemsServicio = new ArrayList<ItemCotizacionServicio>();
	}

	public Cotizacion(Long id, Date fecha, Date fechaVencimiento, Date hora, String formaPago, String estado,
			Vehiculo vehiculo, List<ItemCotizacionArticulo> items, List<ItemCotizacionServicio> itemsServicio,
			OrdenDeTrabajo ordenDeTrabajo) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.fechaVencimiento = fechaVencimiento;
		this.hora = hora;
		this.formaPago = formaPago;
		this.estado = estado;
		this.vehiculo = vehiculo;
		this.items = items;
		this.itemsServicio = itemsServicio;
		this.ordenDeTrabajo = ordenDeTrabajo;
	}

	@PrePersist
	public void prePersist() {
		fecha = new Date();
		hora = new Date();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = sumarRestarDiasFecha(fechaVencimiento);
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public List<ItemCotizacionArticulo> getItems() {
		return items;
	}

	public void setItems(List<ItemCotizacionArticulo> items) {
		this.items = items;
	}

	public List<ItemCotizacionServicio> getItemsServicio() {
		return itemsServicio;
	}

	public void setItemsServicio(List<ItemCotizacionServicio> itemsServicio) {
		this.itemsServicio = itemsServicio;
	}

	public Date sumarRestarDiasFecha(Date fecha) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(fecha); // Configuramos la fecha que se recibe

		calendar.add(Calendar.DAY_OF_YEAR, 1); // numero de días a añadir, o restar en caso de días<0

		return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos

	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public OrdenDeTrabajo getOrdenDeTrabajo() {
		return ordenDeTrabajo;
	}

	public void setOrdenDeTrabajo(OrdenDeTrabajo ordenDeTrabajo) {
		this.ordenDeTrabajo = ordenDeTrabajo;
	}

	public void addItemCotizacionArticulo(ItemCotizacionArticulo item) {

		ItemCotizacionArticulo itemTmp = existenciaArticulo(item);

		if (itemTmp == null) {
			this.items.add(item);
		} else {
			this.items.remove(itemTmp);
			itemTmp.setCantidad(itemTmp.getCantidad() + item.getCantidad());

			this.items.add(itemTmp);

		}

	}

	private ItemCotizacionArticulo existenciaArticulo(ItemCotizacionArticulo item) {
		for (ItemCotizacionArticulo i : this.items) {
			if ((i.getArticulo().getId() == item.getArticulo().getId())
					&& (i.getCotizacion().getId() == item.getCotizacion().getId())) {
				return i;
			}
		}

		return null;
	}

	public void addItemCotizacionServicio(ItemCotizacionServicio item) {
		ItemCotizacionServicio itemTmp = existenciaServicio(item);

		if (itemTmp == null) {
			this.itemsServicio.add(item);
		}

		else {
			this.itemsServicio.remove(itemTmp);
			itemTmp.setCantidad(itemTmp.getCantidad() + item.getCantidad());

			this.itemsServicio.add(itemTmp);

		}

	}

	private ItemCotizacionServicio existenciaServicio(ItemCotizacionServicio item) {
		for (ItemCotizacionServicio i : this.itemsServicio) {
			if ((i.getServicio().getId() == item.getServicio().getId())
					&& (i.getCotizacion().getId() == item.getCotizacion().getId())) {
				return i;
			}
		}

		return null;
	}

	public Double getPrecioCompraArticulo() {

		Double total = 0.0;

		int size = items.size();

		for (int i = 0; i < size; i++) {

			total += items.get(i).getArticulo().getPrecioCompra();

		}

		return total;
	}

	public Double getPrecioCompraServcio() {

		Double total = 0.0;

		int size = itemsServicio.size();

		for (int i = 0; i < size; i++) {

			total += itemsServicio.get(i).getServicio().getPrecio();

		}

		return total;
	}

	public Double totalPrecioUnitarios() {

		return (getPrecioCompraArticulo() + getPrecioCompraServcio());
	}

	public String totalPrecioUnitariosMostrar() {

		if (totalPrecioUnitarios() == 0.0) {
			return "0.0";
		} else {
			Locale.setDefault(Locale.US);
			DecimalFormat num = new DecimalFormat("#,###");
			return num.format(totalPrecioUnitarios());
		}

	}

	public Double getSubTotalArticulo() {

		Double total = 0.0;

		int size = items.size();

		for (int i = 0; i < size; i++) {

			total += items.get(i).subTotal();

		}

		return total;
	}

	public Double getSubTotalServcio() {

		Double total = 0.0;

		int size = itemsServicio.size();

		for (int i = 0; i < size; i++) {

			total += itemsServicio.get(i).subTotal();

		}

		return total;
	}

	public Double sumaSubTotales() {

		return (getSubTotalArticulo() + getSubTotalServcio());
	}

	public String sumaSubTotalesMostrar() {

		if (sumaSubTotales() == 0.0) {
			return "0.0";
		} else {
			Locale.setDefault(Locale.US);
			DecimalFormat num = new DecimalFormat("#,###");
			return num.format(sumaSubTotales());
		}

	}

	public Double getIvaArticulo() {

		Double total = 0.0;

		int size = items.size();

		for (int i = 0; i < size; i++) {

			total += items.get(i).iva();

		}

		return total;
	}

	public Double getIvaServicio() {

		Double total = 0.0;

		int size = itemsServicio.size();

		for (int i = 0; i < size; i++) {

			total += itemsServicio.get(i).iva();

		}

		return total;
	}

	public Double getIvaNeto() {
		return (getIvaArticulo() + getIvaServicio());
	}

	public String getIvaNetoMostrar() {

		if (getIvaNeto() == 0.0) {
			return "0.0";
		} else {
			Locale.setDefault(Locale.US);
			DecimalFormat num = new DecimalFormat("#,###");
			return num.format(getIvaNeto());
		}

	}

	public Double getDescuentoArticulo() {

		Double total = 0.0;

		int size = items.size();

		for (int i = 0; i < size; i++) {

			total += items.get(i).descuento();

		}

		return total;
	}

	public Double getDescuentoServicio() {

		Double total = 0.0;

		int size = itemsServicio.size();

		for (int i = 0; i < size; i++) {

			total += itemsServicio.get(i).descuento();

		}

		return total;
	}

	public Double getDescuentoNeto() {

		return (getDescuentoArticulo() + getDescuentoServicio());
	}

	public String getDescuentoNetoMostrar() {

		if (getDescuentoNeto() == 0.0) {
			return "0.0";
		} else {
			Locale.setDefault(Locale.US);
			DecimalFormat num = new DecimalFormat("#,###");
			return num.format(getDescuentoNeto());
		}

	}

	public Double getTotalArticulo() {

		Double total = 0.0;

		int size = items.size();

		for (int i = 0; i < size; i++) {

			total += items.get(i).calcularImporte();

		}

		return total;
	}

	public Double getTotalServicio() {

		Double total = 0.0;

		int size = itemsServicio.size();

		for (int i = 0; i < size; i++) {

			total += itemsServicio.get(i).calcularImporte();

		}

		return total;
	}

	public Double getTotalNeto() {
		return (getTotalArticulo() + getTotalServicio());
	}

	
	public String getTotalNetoMostrar() {
		
		if(getTotalNeto() == 0.0) {
			return "0.0";
		}else {
		Locale.setDefault(Locale.US);
		DecimalFormat num = new DecimalFormat("#,###");
		return num.format(getTotalNeto());
		}

	}



	public Double getTotalPagar() {
		return getTotalNeto() + getIvaNeto();
	}

	public String getTotalPagarMostrar() {

		if (getTotalPagar() == 0.0) {
			return "0.0";
		} else {
			Locale.setDefault(Locale.US);
			DecimalFormat num = new DecimalFormat("#,###");
			return num.format(getTotalPagar());
		}

	}

	private static final long serialVersionUID = 1L;
}
